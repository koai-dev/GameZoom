package com.koai.gamezoom.ui.main.adapter

import com.koai.base.adapter.BaseListAdapter
import com.koai.gamezoom.R
import com.koai.gamezoom.databinding.ItemSlideBinding
import com.koai.gamezoom.models.Game

class SlideAdapter : BaseListAdapter<Game>() {
    override fun getLayoutId() = R.layout.item_slide
    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder.binding as ItemSlideBinding).apply {
            game = getItem(position)
            executePendingBindings()
        }
    }
}