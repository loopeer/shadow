package com.loopeer.example.shadows


enum class SeekItem {

    PADDING("Padding"),
    SHADOW_RADIUS("Shadow Radius"),
    SHADOW_MARGIN("Shadow Margin"),
    CORNER_RADIUS("Corner Radius"),
    FOREGROUND_COLOR("Foreground Color"),
    SHADOW_COLOR("Shadow Color"),
    BACKGROUND_COLOR("Background Color"),
    SHADOW_DX("Shadow Dx"),
    SHADOW_DY("Shadow Dy"),
    SHADOW_MARGIN_TOP("Shadow Margin Top"),
    SHADOW_MARGIN_BOTTOM("Shadow Margin Bottom"),
    SHADOW_MARGIN_LEFT("Shadow Margin Left"),
    SHADOW_MARGIN_RIGHT("Shadow Margin Right"),
    CORNER_RADIUS_TOP_LEFT("Corner Radius Top Left"),
    CORNER_RADIUS_TOP_RIGHT("Corner Radius Top Right"),
    CORNER_RADIUS_BOTTOM_RIGHT("Corner Radius Bottom Right"),
    CORNER_RADIUS_BOTTOM_LEFT("Corner Radius Bottom Left"),;

    var title: String

    constructor(title: String) {
        this.title = title
    }


}