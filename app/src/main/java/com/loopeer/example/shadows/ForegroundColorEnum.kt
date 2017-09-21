package com.loopeer.example.shadows

enum class ForegroundColorEnum {
    RED("#f44336"),
    LIGHT_BLUE("#03a9f4"),
    GREEN("#4caf50"),
    YELLOW("#ffeb3b"),
    DEEP_ORANGE("#ff5722"),
    BLUE_GREY("#607d8b"),
    DEFAULT_LIGHT("#1f000000"),
    DEFAULT_DARK("#33ffffff"),;

    var color: String

    constructor(color: String) {
        this.color = color
    }
}