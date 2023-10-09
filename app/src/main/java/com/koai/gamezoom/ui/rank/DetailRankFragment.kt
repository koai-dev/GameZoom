package com.koai.gamezoom.ui.rank

import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.koai.base.ui.activity.BaseActivity
import com.koai.base.ui.fragment.BaseFragment
import com.koai.gamezoom.GameApplication
import com.koai.gamezoom.R
import com.koai.gamezoom.api.ApiFirebase
import com.koai.gamezoom.databinding.FragmentDetailRankBinding
import com.koai.gamezoom.db.viewmodel.GameViewModel
import com.koai.gamezoom.db.viewmodel.GameViewModelFactory
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.ui.rank.adapter.RankAdapter

class DetailRankFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailRankBinding
    var game: Game? = null
    private lateinit var adapter: RankAdapter
    private lateinit var activity: BaseActivity
    private val gameViewModel: GameViewModel by viewModels { GameViewModelFactory((requireActivity().application as GameApplication).repository) }
    override fun getBindingView(container: ViewGroup?): ViewBinding {
        return DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_detail_rank,
            container,
            false
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: ViewBinding) {
        this.binding = binding as FragmentDetailRankBinding
        activity = requireActivity() as BaseActivity
        if (game?.name != null) {
            binding.title = "Top ${game?.name}"
        } else {
            binding.title = "History"
        }
        setRcv()
        observer()
    }

    private fun setRcv() {
        adapter = if (game?.name == null) {
            RankAdapter(0)
        } else {
            RankAdapter(1)
        }
        binding.rcvContent.adapter = adapter
    }

    private fun observer() {
        if (game?.name == null) {
            gameViewModel.allGameLocal.observe(activity) {
                adapter.submitList(it?.sortedByDescending { game -> game.timestamp })
                binding.hasData = !it.isNullOrEmpty()
            }
        } else {
            ApiFirebase.getGamesRank(game!!.type!!, object : ApiFirebase.GameListener {
                override fun onSuccess(games: List<Game>?) {
                    adapter.submitList(games?.sortedByDescending { game -> game.score })
                    binding.hasData = !games.isNullOrEmpty()
                }

                override fun onError(mess: String?) {
                    binding.hasData = false
                }

            })
        }
    }
}