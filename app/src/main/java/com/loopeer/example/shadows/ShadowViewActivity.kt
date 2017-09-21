package com.loopeer.example.shadows

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_shadow_view.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.loopeer.shadow.ShadowView
import kotlinx.android.synthetic.main.list_item_seek_select.view.*


class ShadowViewActivity : AppCompatActivity() {

    lateinit var adapter: ShadowViewRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shadow_view)

        adapter = ShadowViewRecyclerAdapter(shadow_view)
        view_recycler.adapter = adapter
        view_recycler.layoutManager = LinearLayoutManager(this)
    }

    class ShadowViewRecyclerAdapter(val shadowView: ShadowView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent?.context)
            return ShadowViewSeekItemHolder(inflater.inflate(R.layout.list_item_seek_select, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            holder?.let {
                val itemView = holder.itemView
                val seekItem = SeekItem.values()[position]
                itemView.text_title.text = seekItem.title
                when (seekItem) {
                    SeekItem.SHADOW_RADIUS -> {
                        itemView.text_value.text = shadowView.shadowRadius.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.shadowRadius = p.toFloat()
                            itemView.text_value.text = p.toString()
                        })
                    }
                    SeekItem.PADDING -> {
                        itemView.text_value.text = shadowView.paddingLeft.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.setPadding(p, p, p, p)
                            itemView.text_value.text = p.toString()
                        })
                    }
                    SeekItem.SHADOW_MARGIN -> {
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.setShadowMargin(p, p, p, p)
                            itemView.text_value.text = p.toString()
                        })
                    }
                    SeekItem.SHADOW_MARGIN_LEFT -> {
                        itemView.text_value.text = shadowView.shadowMarginLeft.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.shadowMarginLeft = p
                            itemView.text_value.text = p.toString()
                            shadowView.requestLayout()
                        })
                    }
                    SeekItem.SHADOW_MARGIN_TOP -> {
                        itemView.text_value.text = shadowView.shadowMarginTop.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.shadowMarginTop = p
                            itemView.text_value.text = p.toString()
                            shadowView.requestLayout()
                        })
                    }
                    SeekItem.SHADOW_MARGIN_RIGHT -> {
                        itemView.text_value.text = shadowView.shadowMarginRight.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.shadowMarginRight = p
                            itemView.text_value.text = p.toString()
                            shadowView.requestLayout()
                        })
                    }
                    SeekItem.SHADOW_MARGIN_BOTTOM -> {
                        itemView.text_value.text = shadowView.shadowMarginBottom.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.shadowMarginBottom = p
                            itemView.text_value.text = p.toString()
                            shadowView.requestLayout()
                        })
                    }
                    SeekItem.CORNER_RADIUS -> {
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            val p = p.toFloat()
                            shadowView.setCornerRadius(p, p, p, p)
                            itemView.text_value.text = p.toString()
                        })
                    }
                    SeekItem.CORNER_RADIUS_TOP_LEFT -> {
                        itemView.text_value.text = shadowView.cornerRadiusTL.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.cornerRadiusTL = p.toFloat()
                            itemView.text_value.text = p.toString()
                            shadowView.invalidate()
                        })
                    }
                    SeekItem.CORNER_RADIUS_TOP_RIGHT -> {
                        itemView.text_value.text = shadowView.cornerRadiusTR.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.cornerRadiusTR = p.toFloat()
                            itemView.text_value.text = p.toString()
                            shadowView.invalidate()
                        })
                    }
                    SeekItem.CORNER_RADIUS_BOTTOM_RIGHT -> {
                        itemView.text_value.text = shadowView.cornerRadiusBR.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.cornerRadiusBR = p.toFloat()
                            itemView.text_value.text = p.toString()
                            shadowView.invalidate()
                        })
                    }
                    SeekItem.CORNER_RADIUS_BOTTOM_LEFT -> {
                        itemView.text_value.text = shadowView.cornerRadiusBL.toString()
                        itemView.seek_bar.onProgressChange({ _, p, _ ->
                            shadowView.cornerRadiusBL = p.toFloat()
                            itemView.text_value.text = p.toString()
                            shadowView.invalidate()
                        })
                    }
                }
            }
        }

        override fun getItemCount() = SeekItem.values().size

    }

    class ShadowViewSeekItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun onShadowClickTest(view: View) {}

}
