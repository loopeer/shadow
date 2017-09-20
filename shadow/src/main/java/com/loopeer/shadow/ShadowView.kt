package com.loopeer.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup


class ShadowView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleInt: Int = 0)
    : ViewGroup(context, attributeSet, defStyleInt) {
    private val DEFAULT_CHILD_GRAVITY = Gravity.TOP or Gravity.START

    private var SIZE_UNSET = 0
    private var foregroundDraw: Drawable? = null
    private val selfBounds = Rect()
    private val overlayBounds = Rect()
    private var foregroundDrawGravity = Gravity.FILL
    private var foregroundDrawInPadding = true
    private var foregroundDrawBoundsChanged = false

    private val bgPaint = Paint()
    var shadowColor: Int = 0
    var foregroundColor: Int = 0
        set(value) {
            field = value
            updateForegroundColor()
        }
    var offset = 7

    var shadowRadius = 7f
        set(value) {
            field = value
            bgPaint.setShadowLayer(value, 0f, 0f,
                    shadowColor)
            offset = value.toInt()
            invalidate()
        }
    var radius = 20f
    var shadowMargin: Int

    init {
        val a = getContext().obtainStyledAttributes(attributeSet, R.styleable.ShadowView,
                defStyleInt, 0)
        val d = a.getDrawable(R.styleable.ShadowView_android_foreground)
        if (d != null) {
            setForeground(foregroundDraw)
        }
        shadowMargin = a.getDimensionPixelSize(
                R.styleable.ShadowView_shadowMargin, SIZE_UNSET)
        a.recycle()
        foregroundColor = Color.parseColor("#1f000000")//dark #33ffffff light 1f000000

        shadowColor = Color.parseColor("#FAC4BA")//FAC4BA E3E5E7
        bgPaint.color = Color.WHITE
        bgPaint.isAntiAlias = true
        bgPaint.style = Paint.Style.FILL
        bgPaint.setShadowLayer(shadowRadius, 0f, 1f,
                shadowColor)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxHeight = 0
        var maxWidth = 0
        var childState = 0

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
        val shadowMeasureWidthMatchParent = layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT
        val shadowMeasureHeightMatchParent = layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT
        var widthSpec = widthMeasureSpec
        if (shadowMeasureWidthMatchParent) {
            val childWidthSize = measuredWidth - getShadowMarginRight() - getShadowMarginLeft()
            widthSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
        }
        var heightSpec = heightMeasureSpec
        if (shadowMeasureHeightMatchParent) {
            val childHeightSize = measuredHeight - getShadowMarginTop() - getShadowMarginBottom()
            heightSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY)
        }
        val child = getChildAt(0)
        if (child.visibility !== View.GONE) {
            measureChildWithMargins(child, widthSpec, 0, heightSpec, 0)
            val lp = child.layoutParams as LayoutParams
            maxWidth =
                    if (shadowMeasureWidthMatchParent)
                        Math.max(maxWidth,
                                child.measuredWidth + lp.leftMargin + lp.rightMargin)
                    else
                        Math.max(maxWidth,
                                child.measuredWidth + getShadowMarginLeft() + getShadowMarginRight() + lp.leftMargin + lp.rightMargin)
            maxHeight =
                    if (shadowMeasureHeightMatchParent)
                        Math.max(maxHeight,
                                child.measuredHeight + getShadowMarginTop() + getShadowMarginBottom() + lp.topMargin + lp.bottomMargin)
                    else
                        Math.max(maxHeight,
                                child.measuredHeight + getShadowMarginTop() + getShadowMarginBottom() + lp.topMargin + lp.bottomMargin)

            childState = View.combineMeasuredStates(childState, child.measuredState)
        }
        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom
        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)
        val drawable = foreground
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.minimumHeight)
            maxWidth = Math.max(maxWidth, drawable.minimumWidth)
        }
        setMeasuredDimension(View.resolveSizeAndState(maxWidth
                , if (shadowMeasureWidthMatchParent) widthMeasureSpec else widthSpec, childState),
                View.resolveSizeAndState(maxHeight
                        , if (shadowMeasureHeightMatchParent) heightMeasureSpec else heightSpec,
                        childState shl View.MEASURED_HEIGHT_STATE_SHIFT))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        layoutChildren(left, top, right, bottom, false)
        if (changed)
            foregroundDrawBoundsChanged = changed
    }

    private fun layoutChildren(left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
        val count = childCount

        val parentLeft = getPaddingLeftWithForeground()
        val parentRight = right - left - getPaddingRightWithForeground()

        val parentTop = getPaddingTopWithForeground()
        val parentBottom = bottom - top - getPaddingBottomWithForeground()

        for (i in 0..(count - 1)) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as LayoutParams

                val width = child.measuredWidth
                val height = child.measuredHeight

                var childLeft = 0
                var childTop: Int

                var gravity = lp.gravity
                if (gravity == -1) {
                    gravity = DEFAULT_CHILD_GRAVITY
                }

                val layoutDirection = layoutDirection
                val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
                val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK

                when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
                            lp.leftMargin - lp.rightMargin + getShadowMarginLeft() - getShadowMarginRight()
                    Gravity.RIGHT -> {
                        if (!forceLeftGravity) {
                            childLeft = parentRight - width - lp.rightMargin - getShadowMarginRight()
                        }
                    }
                    Gravity.LEFT -> {
                        childLeft = parentLeft + lp.leftMargin + getShadowMarginLeft()
                    }
                    else -> childLeft = parentLeft + lp.leftMargin + getShadowMarginLeft()
                }

                when (verticalGravity) {
                    Gravity.TOP -> childTop = parentTop + lp.topMargin + getShadowMarginTop()
                    Gravity.CENTER_VERTICAL -> childTop = parentTop + (parentBottom - parentTop - height) / 2 +
                            lp.topMargin - lp.bottomMargin + getShadowMarginTop() - getShadowMarginBottom()
                    Gravity.BOTTOM -> childTop = parentBottom - height - lp.bottomMargin - getShadowMarginBottom()
                    else -> childTop = parentTop + lp.topMargin + getShadowMarginTop()
                }

                child.layout(childLeft, childTop, childLeft + width, childTop + height)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val w = measuredWidth
            val h = measuredHeight
            val rect = Rect(shadowMargin, shadowMargin, w - shadowMargin, h - shadowMargin)
            val rectF = RectF(rect)
            it.drawRoundRect(rectF, radius, radius, bgPaint)
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.let {
            canvas.save()
            val w = measuredWidth
            val h = measuredHeight
            val path = ShapeUtils.roundedRect(shadowMargin.toFloat(), shadowMargin.toFloat(), (w - shadowMargin).toFloat()
                    , (h - shadowMargin).toFloat(), radius, radius)
            canvas.clipPath(path)
            drawForeground(canvas)
            canvas.restore()
        }
    }

    fun drawForeground(canvas: Canvas?) {
        foregroundDraw?.let {
            if (foregroundDrawBoundsChanged) {
                foregroundDrawBoundsChanged = false
                val w = right - left
                val h = bottom - top
                if (foregroundDrawInPadding) {
                    selfBounds.set(0, 0, w, h)
                } else {
                    selfBounds.set(paddingLeft, paddingTop,
                            w - paddingRight, h - paddingBottom)
                }
                Gravity.apply(foregroundDrawGravity, it.intrinsicWidth,
                        it.intrinsicHeight, selfBounds, overlayBounds)
                it.bounds = overlayBounds
            }
            it.draw(canvas)
        }
    }

    override fun getForeground(): Drawable? {
        return foregroundDraw
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foregroundDrawBoundsChanged = true
    }

    override fun getForegroundGravity(): Int {
        return foregroundDrawGravity
    }

    override fun setForegroundGravity(foregroundGravity: Int) {
        var foregroundGravity = foregroundGravity
        if (foregroundDrawGravity != foregroundGravity) {
            if (foregroundGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK == 0) {
                foregroundGravity = foregroundGravity or Gravity.START
            }

            if (foregroundGravity and Gravity.VERTICAL_GRAVITY_MASK == 0) {
                foregroundGravity = foregroundGravity or Gravity.TOP
            }

            foregroundDrawGravity = foregroundGravity


            if (foregroundDrawGravity == Gravity.FILL && foregroundDraw != null) {
                val padding = Rect()
                foregroundDraw?.getPadding(padding)
            }

            requestLayout()
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === foregroundDraw
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        foregroundDraw?.let { it.jumpToCurrentState() }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        foregroundDraw?.takeIf { it.isStateful }?.let { it.state = drawableState }
    }

    override fun setForeground(drawable: Drawable?) {
        if (foregroundDraw != null) {
            foregroundDraw?.callback = null
            unscheduleDrawable(foregroundDraw)
        }
        foregroundDraw = drawable

        updateForegroundColor()

        if (drawable != null) {
            setWillNotDraw(false)
            drawable.callback = this
            if (drawable.isStateful) {
                drawable.state = drawableState
            }
            if (foregroundDrawGravity == Gravity.FILL) {
                val padding = Rect()
                drawable.getPadding(padding)
            }
        }
        requestLayout()
        invalidate()
    }

    private fun updateForegroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (foregroundDraw as RippleDrawable?)?.setColor(ColorStateList.valueOf(foregroundColor))
        } else {
            foregroundDraw?.setColorFilter(foregroundColor, PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            foregroundDraw?.let { it.setHotspot(x, y) }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(lp)
    }

    override fun getAccessibilityClassName(): CharSequence {
        return FrameLayout::class.java.name
    }

    fun getPaddingLeftWithForeground(): Int {
        return paddingLeft
    }

    fun getPaddingRightWithForeground(): Int {
        return paddingRight
    }

    private fun getPaddingTopWithForeground(): Int {
        return paddingTop
    }

    private fun getPaddingBottomWithForeground(): Int {
        return paddingBottom
    }

    private fun getShadowMarginLeft(): Int {
        return shadowMargin
    }

    private fun getShadowMarginTop(): Int {
        return shadowMargin
    }

    private fun getShadowMarginRight(): Int {
        return shadowMargin
    }

    private fun getShadowMarginBottom(): Int {
        return shadowMargin
    }

    class LayoutParams : ViewGroup.MarginLayoutParams {

        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         *
         *
         * The default value is [.UNSPECIFIED_GRAVITY], which is treated
         * by FrameLayout as `Gravity.TOP | Gravity.START`.
         *
         * @see android.view.Gravity
         *
         * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
         */
        var gravity = UNSPECIFIED_GRAVITY

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {

            val a = c.obtainStyledAttributes(attrs, R.styleable.ShadowView_Layout)
            gravity = a.getInt(R.styleable.ShadowView_Layout_layout_gravity, UNSPECIFIED_GRAVITY)
            a.recycle()
        }

        constructor(width: Int, height: Int) : super(width, height) {}

        constructor(width: Int, height: Int, gravity: Int) : super(width, height) {
            this.gravity = gravity
        }

        constructor(source: ViewGroup.LayoutParams) : super(source) {}

        constructor(source: ViewGroup.MarginLayoutParams) : super(source) {}

        constructor(source: LayoutParams) : super(source) {

            this.gravity = source.gravity
        }

        companion object {
            val UNSPECIFIED_GRAVITY = -1
        }
    }
}