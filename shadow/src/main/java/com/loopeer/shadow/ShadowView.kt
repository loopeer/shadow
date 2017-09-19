package com.loopeer.shadow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import android.os.Build

class ShadowView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleInt: Int = 0)
    : FrameLayout(context, attributeSet, defStyleInt) {

    private val bgPaint = Paint()
    var shadowColor: Int = 0
    var offset = 10
        set(value) {
            field = value
            invalidate()
        }

    var shadowRadius = 7f
        set(value) {
            field = value
            bgPaint.setShadowLayer(value, 0f, 0f,
                    shadowColor)
            invalidate()
        }
    var radius = 0f

    init {
        shadowColor = Color.parseColor("#FAC4BA")//FAC4BA E3E5E7
        bgPaint.color = Color.WHITE
        bgPaint.isAntiAlias = true
        bgPaint.style = Paint.Style.FILL
        bgPaint.setShadowLayer(shadowRadius, 0f, 1f,
                shadowColor)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val r = 20f
            val w = measuredWidth
            val h = measuredHeight
            val rect = Rect(offset, offset, w - offset, h - offset)
            val rectF = RectF(rect)
            it.drawRoundRect(rectF, r, r, bgPaint)
        }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        canvas?.let {
            canvas.save()
            val path = Path()
            val w = measuredWidth
            val h = measuredHeight
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                path.addRoundRect(offset.toFloat(), offset.toFloat(), (w - offset).toFloat(), (h - offset).toFloat(), 20f, 20f, Path.Direction.CW)
                canvas.clipPath(path)
            }
            super.onDrawForeground(canvas)
            canvas.restore()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }


}