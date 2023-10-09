package com.koai.gamezoom.ui.rank.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.utils.GameUtils
import com.koai.gamezoom.ui.rank.DetailRankFragment

class RankPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = (GameUtils.listGame?.size ?: 0) + 1

    override fun createFragment(position: Int): Fragment {
        return DetailRankFragment().apply {
            game = if (position == 0) {
                Game()
            } else {
                GameUtils.listGame?.get(position - 1)
            }
        }
    }
}