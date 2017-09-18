package com.loopeer.example.shadows

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_shadow_view.*

class ShadowViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shadow_view)

        seek_shadow_radius.onProgressChange({ _, p, _ -> shadow_view.shadowRadius = p.toFloat() })
        seek_padding.onProgressChange({ _, p, _ -> shadow_view.setPadding(p, p, p, p)})
        seek_margin.onProgressChange({ _, p, _ ->
            (shadow_view.layoutParams as ViewGroup.MarginLayoutParams).setMargins(p, p, p, p)
            shadow_view.requestLayout()
        })
    }
}
