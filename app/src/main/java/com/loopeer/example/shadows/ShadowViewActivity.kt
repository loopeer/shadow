package com.loopeer.example.shadows

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_shadow_view.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.google.android.flexbox.FlexboxLayout
import com.loopeer.shadow.ShadowView
import kotlinx.android.synthetic.main.list_item_color_select.view.*
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
            return when (viewType) {
                R.layout.list_item_color_select -> {
                    ShadowViewColorItemHolder(inflater.inflate(R.layout.list_item_color_select, parent, false))
                }
                else -> {
                    ShadowViewSeekItemHolder(inflater.inflate(R.layout.list_item_seek_select, parent, false))
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is ShadowViewSeekItemHolder) {
                holder.bind(SeekItem.values()[position], shadowView)
            } else if (holder is ShadowViewColorItemHolder) {
                holder.bind(SeekItem.values()[position], shadowView)
                holder.onClickColor = {notifyItemChanged(position)}
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (SeekItem.values()[position]) {
                SeekItem.FOREGROUND_COLOR,
                SeekItem.SHADOW_COLOR -> {
                    R.layout.list_item_color_select
                }
                else -> {
                    R.layout.list_item_seek_select
                }
            }
        }

        override fun getItemCount() = SeekItem.values().size

    }

    class ShadowViewSeekItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(seekItem: SeekItem, shadowView: ShadowView) {
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
    class ShadowViewColorItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var onClickColor: (() -> Unit)? = null

        fun bind(seekItem: SeekItem, shadowView: ShadowView) {
            itemView.text_color_title.text = seekItem.title
            val inflater = LayoutInflater.from(itemView.context)
            for (item in ColorEnum.values()) {
                var view: View? = null
                if (itemView.view_flex.childCount != ColorEnum.values().size) {
                    view = inflater.inflate(R.layout.view_item_color, itemView.view_flex, false)
                    val p = FlexboxLayout.LayoutParams(dp2px(42f, itemView.context), dp2px(24f, itemView.context))
                    val margin = dp2px(4f, itemView.context)
                    p.setMargins(margin, margin, margin, margin)
                    itemView.view_flex.addView(view, p)
                } else {
                    view = itemView.view_flex.getChildAt(item.ordinal)
                }
                view?.let {
                    it.setBackgroundColor(Color.parseColor(item.color))
                    when (seekItem) {
                        SeekItem.SHADOW_COLOR -> {
                            it.isSelected = Color.parseColor(item.color) == shadowView.shadowColor
                            it.setOnClickListener {
                                shadowView.shadowColor = Color.parseColor(item.color)
                                onClickColor?.invoke()
                            }
                        }
                        SeekItem.FOREGROUND_COLOR -> {
                            it.isSelected = Color.parseColor(item.color) == shadowView.foregroundColor
                            it.setOnClickListener {
                                shadowView.foregroundColor = Color.parseColor(item.color)
                                onClickColor?.invoke()
                            }
                        }

                    }
                }
            }
        }

        fun dp2px(dipValue: Float, context: Context): Int {
            val metrics = context.resources.displayMetrics
            return (dipValue * metrics.density + 0.5f).toInt()
        }
    }

    fun onShadowClickTest(view: View) {}

}
