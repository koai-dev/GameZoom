package com.koai.gamezoom.ui.rank.adapter

import com.koai.base.adapter.BaseListAdapter
import com.koai.gamezoom.R
import com.koai.gamezoom.databinding.ItemHistoryBinding
import com.koai.gamezoom.databinding.ItemRankBinding
import com.koai.gamezoom.models.Game

class RankAdapter(val type: Int/*type 0: history, 1: rank*/) : BaseListAdapter<Game>() {
    override fun getLayoutId(): Int {
        return if (type == 0) {
            R.layout.item_history
        } else {
            R.layout.item_rank
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (type == 0) {
            (holder.binding as ItemHistoryBinding).apply {
                pos = holder.bindingAdapterPosition
                game = getItem(holder.bindingAdapterPosition)

                executePendingBindings()
            }
        } else {
            (holder.binding as ItemRankBinding).apply {
                pos = holder.bindingAdapterPosition
                txtName.isSelected = true
                game = getItem(position)
                executePendingBindings()
            }
        }
    }
}