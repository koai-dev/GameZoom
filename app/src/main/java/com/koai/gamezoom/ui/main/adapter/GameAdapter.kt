package com.koai.gamezoom.ui.main.adapter

import com.koai.base.adapter.BaseListAdapter
import com.koai.gamezoom.R
import com.koai.gamezoom.databinding.ItemGameBinding
import com.koai.gamezoom.models.Game

class GameAdapter : BaseListAdapter<Game>() {
    override fun getLayoutId(): Int = R.layout.item_game
    override fun onBindViewHolder(holder: VH, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder.binding as ItemGameBinding).apply {
            game = getItem(position)
            executePendingBindings()
        }
    }
}