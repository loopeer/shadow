package com.loopeer.shadow

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.os.Build
import android.view.Gravity

class ShadowView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleInt: Int = 0)
    : FrameLayout(context, attributeSet, defStyleInt) {

    private var foregroundDraw: Drawable? = null
    private val selfBounds = Rect()
    private val overlayBounds = Rect()
    private var foregroundDrawGravity = Gravity.FILL
    protected var foregroundDrawInPadding = true
    internal var foregroundDrawBoundsChanged = false

    private val bgPaint = Paint()
    var shadowColor: Int = 0
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

    init {
        val a = getContext().obtainStyledAttributes(attributeSet, R.styleable.ShadowView,
                defStyleInt, 0)
        val d = a.getDrawable(R.styleable.ShadowView_android_foreground)
        if (d != null) {
            setForeground(foregroundDraw)
        }
        a.recycle()

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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val w = measuredWidth
            val h = measuredHeight
            val rect = Rect(offset, offset, w - offset, h - offset)
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
            val path = roundedRect(offset.toFloat(), offset.toFloat(), (w - offset).toFloat(), (h - offset).toFloat(), radius, radius)
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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed)
            foregroundDrawBoundsChanged = changed
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
        if (foregroundDraw !== drawable) {
            if (foregroundDraw != null) {
                foregroundDraw?.callback = null
                unscheduleDrawable(foregroundDraw)
            }

            foregroundDraw = drawable

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
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            foregroundDraw?.let { it.setHotspot(x, y) }
    }

    fun roundedRect(
            left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float,
            tl: Boolean = true, tr: Boolean = true, br: Boolean = true, bl: Boolean = true): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, top + ry)
        if (tr)
            path.rQuadTo(0f, -ry, -rx, -ry)//top-right corner
        else {
            path.rLineTo(0f, -ry)
            path.rLineTo(-rx, 0f)
        }
        path.rLineTo(-widthMinusCorners, 0f)
        if (tl)
            path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        else {
            path.rLineTo(-rx, 0f)
            path.rLineTo(0f, ry)
        }
        path.rLineTo(0f, heightMinusCorners)

        if (bl)
            path.rQuadTo(0f, ry, rx, ry)//bottom-left corner
        else {
            path.rLineTo(0f, ry)
            path.rLineTo(rx, 0f)
        }

        path.rLineTo(widthMinusCorners, 0f)
        if (br)
            path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
        else {
            path.rLineTo(rx, 0f)
            path.rLineTo(0f, -ry)
        }

        path.rLineTo(0f, -heightMinusCorners)

        path.close()//Given close, last lineto can be removed.

        return path
    }
}