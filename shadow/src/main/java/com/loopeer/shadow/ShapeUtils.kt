package com.loopeer.shadow

import android.graphics.Path


object ShapeUtils {

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

    fun roundedRect(
            left: Float, top: Float, right: Float, bottom: Float,
            tl: Float, tr: Float, br: Float, bl: Float): Path {
        var tl = tl
        var tr = tr
        var br = br
        var bl = bl
        val path = Path()
        if (tl < 0) tl = 0f
        if (tr < 0) tr = 0f
        if (br < 0) br = 0f
        if (bl < 0) bl = 0f
        val width = right - left
        val height = bottom - top
        if (tl > Math.min(width, height) / 2) tl = Math.min(width, height) / 2
        if (tr > Math.min(width, height) / 2) tr = Math.min(width, height) / 2
        if (br > Math.min(width, height) / 2) br = Math.min(width, height) / 2
        if (bl > Math.min(width, height) / 2) bl = Math.min(width, height) / 2
//        val widthMinusCorners = width - 2 * rx
//        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, top + tr)
        if (tr > 0)
            path.rQuadTo(0f, -tr, -tr, -tr)//top-right corner
        else {
            path.rLineTo(0f, -tr)
            path.rLineTo(-tr, 0f)
        }
        path.rLineTo(-(width - tr - tl), 0f)
        if (tl > 0)
            path.rQuadTo(-tl, 0f, -tl, tl) //top-left corner
        else {
            path.rLineTo(-tl, 0f)
            path.rLineTo(0f, tl)
        }
        path.rLineTo(0f, height - tl - bl)

        if (bl > 0)
            path.rQuadTo(0f, bl, bl, bl)//bottom-left corner
        else {
            path.rLineTo(0f, bl)
            path.rLineTo(bl, 0f)
        }

        path.rLineTo(width - bl - br, 0f)
        if (br > 0)
            path.rQuadTo(br, 0f, br, -br) //bottom-right corner
        else {
            path.rLineTo(br, 0f)
            path.rLineTo(0f, -br)
        }

        path.rLineTo(0f, -(height - br - tr))

        path.close()//Given close, last lineto can be removed.

        return path
    }

}