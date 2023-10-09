package com.koai.gamezoom.ui.rank

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.koai.base.ui.fragment.BaseFragment
import com.koai.gamezoom.databinding.FragmentRankBinding
import com.koai.gamezoom.utils.GameUtils
import com.koai.gamezoom.ui.main.MainActivity
import com.koai.gamezoom.ui.rank.adapter.RankPagerAdapter

class RankFragment : BaseFragment() {
    private lateinit var binding: FragmentRankBinding
    private lateinit var rankPagerAdapter: RankPagerAdapter
    private lateinit var activity: MainActivity
    override fun getBindingView(container: ViewGroup?) = FragmentRankBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?, binding: ViewBinding) {
        this.binding = binding as FragmentRankBinding
        activity = requireActivity() as MainActivity
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val paramsTop = binding.pointTop.layoutParams as ViewGroup.MarginLayoutParams
            paramsTop.setMargins(
                0, activity.statusBarHeight, 0, 0
            )
            binding.pointTop.layoutParams = paramsTop

            val paramsBot = binding.pointBot.layoutParams as ViewGroup.MarginLayoutParams
            paramsBot.setMargins(
                0, 0, 0, activity.bottomNavigationHeight
            )
            binding.pointBot.layoutParams = paramsBot
            insets
        }

        setPager()
        setClick()
    }

    private fun setPager() {
        rankPagerAdapter = RankPagerAdapter(activity)
        binding.pagerMain.adapter = rankPagerAdapter
        binding.pagerMain.isUserInputEnabled = true
        binding.pagerMain.offscreenPageLimit = (GameUtils.listGame?.size ?: 0) + 1
    }

    private fun setClick() {
        binding.btnClose.setOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (binding.pagerMain.currentItem < rankPagerAdapter.itemCount - 1) {
                binding.pagerMain.setCurrentItem(binding.pagerMain.currentItem + 1, true)
            } else {
                binding.pagerMain.setCurrentItem(0, true)
            }
        }

        binding.btnBack.setOnClickListener {
            if (binding.pagerMain.currentItem > 0) {
                binding.pagerMain.setCurrentItem(binding.pagerMain.currentItem - 1, true)
            } else {
                binding.pagerMain.setCurrentItem(rankPagerAdapter.itemCount - 1, true)
            }
        }
    }
}