package com.loopeer.example.shadows

import android.widget.SeekBar

inline fun SeekBar.onProgressChange(crossinline progressChange: (p0: SeekBar?, p1: Int, p2: Boolean) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            progressChange(p0, p1, p2)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    })
}
