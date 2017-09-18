package com.loopeer.example.shadows

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onCardItemClick(view: View) {
        startActivity(Intent(this, CardViewActivity::class.java))
    }

    fun onShadowItemClick(view: View) {
        startActivity(Intent(this, ShadowViewActivity::class.java))
    }
}
