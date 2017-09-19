package com.loopeer.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.FrameLayout
import android.graphics.RectF

class ShadowView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleInt: Int = 0)
    : FrameLayout(context, attributeSet, defStyleInt) {

    private val bgPaint = Paint()
    var shadowColor : Int = 0
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

    init {
        shadowColor = Color.parseColor("#E3E5E7")
        bgPaint.color = Color.WHITE
        bgPaint.isAntiAlias = true
        bgPaint.style = Paint.Style.FILL
        bgPaint.setShadowLayer(shadowRadius, 0f, 1f,
                shadowColor)
        setLayerType(LAYER_TYPE_SOFTWARE, bgPaint)
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val r = 4f
            val w = measuredWidth
            val h = measuredHeight
            val rect = Rect(offset, offset, w - offset, h - offset)
            val rectF = RectF(rect)
            it.drawRoundRect(rectF, r, r, bgPaint)
        }
        super.onDraw(canvas)
    }

}