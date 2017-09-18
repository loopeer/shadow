package com.loopeer.example.shadows

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_card_view.*

class CardViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        seek_elevation.onProgressChange({ _, p, _ -> card_view.cardElevation = p.toFloat() })
        seek_padding.onProgressChange({ _, p, _ -> card_view.setContentPadding(p, p, p, p)})
        seek_margin.onProgressChange({ _, p, _ ->
            (card_view.layoutParams as ViewGroup.MarginLayoutParams).setMargins(p, p, p, p)
            card_view.requestLayout()
        })
    }
}
