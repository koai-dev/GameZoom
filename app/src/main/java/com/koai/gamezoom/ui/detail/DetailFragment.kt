package com.koai.gamezoom.ui.detail

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.game.BlockPuzzleActivity
import com.game.knife.KnifeActivity
import com.game2048koai.a2048.MainMenuActivity
import com.koai.base.ui.fragment.BaseFragment
import com.koai.gamezoom.GameApplication
import com.koai.gamezoom.R
import com.koai.gamezoom.account.AccountUtils
import com.koai.gamezoom.databinding.FragmentDetailBinding
import com.koai.gamezoom.db.viewmodel.GameViewModel
import com.koai.gamezoom.db.viewmodel.GameViewModelFactory
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.ui.customView.UpdateDialog
import com.koai.gamezoom.ui.main.MainActivity
import com.koai.gamezoom.utils.AdmobUtils
import com.koai.gamezoom.utils.AppPurchaseUtils
import com.koaidraw.draw.activity.DrawActivity
import com.puzzle15.npuzzle.Main15PuzzleActivity


class DetailFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val gameViewModel: GameViewModel by viewModels { GameViewModelFactory((requireActivity().application as GameApplication).repository) }
    var typeGame = Game.TYPE_FARM_PUZZLE
    private lateinit var activity: MainActivity
    private lateinit var updateDialog: UpdateDialog
    private lateinit var appPurchase: AppPurchaseUtils


    override fun getBindingView(container: ViewGroup?): ViewBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail, container, false)

    override fun initView(savedInstanceState: Bundle?, binding: ViewBinding) {
        this.binding = binding as FragmentDetailBinding
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
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        appPurchase = AppPurchaseUtils(activity, object : AppPurchaseUtils.Result {
            override fun onSuccess() {
                if (AccountUtils.isLogin()) {
                    detailViewModel.onBuySuccess(productId = typeGame)
                } else {
                    gameViewModel.insert(Game(isBuy = true, type = typeGame, productId = typeGame))
                }
            }

            override fun onFail() {

            }

        })
        getData()
        setClick()
        observer()
        checkVip()
    }

    private fun checkVip() {
        gameViewModel.allGameLocal.observe(activity) {
            if (it?.firstOrNull { game -> game.isBuy && (game.type == typeGame || game.productId == typeGame) } != null) {
                binding.btnBuyGame.text = resources.getString(R.string.owned)
                binding.btnBuyGame.isClickable = false
                binding.btnBuyGame.isEnabled = false
            }
        }
    }

    private fun setClick() {
        binding.btnBack.setOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

        binding.btnBuyGame.setOnClickListener {
            appPurchase.purchase(activity, productId = "game10k")
        }

        binding.btnPlay.setOnClickListener {
            AdmobUtils.showAdmob(activity, object : AdmobUtils.Action {
                override fun onReward() {
                    when (typeGame) {
                        Game.TYPE_KNIFE -> {
                            detailViewModel.updatePLayCount(Game.TYPE_KNIFE)
                            openActivity(KnifeActivity::class.java, canBack = true)
                        }

                        Game.TYPE_JUMP_PRINCESS -> {
                            detailViewModel.updatePLayCount(Game.TYPE_JUMP_PRINCESS)
                            openActivity(com.game.jumpboy.MainActivity::class.java, canBack = true)
                        }

                        Game.TYPE_FARM_PUZZLE -> {
                            detailViewModel.updatePLayCount(Game.TYPE_FARM_PUZZLE)
                            openActivity(BlockPuzzleActivity::class.java, canBack = true)
                        }

                        Game.TYPE_2048 -> {
                            detailViewModel.updatePLayCount(Game.TYPE_2048)
                            openActivity(MainMenuActivity::class.java, canBack = true)
                        }

                        Game.TYPE_15_PUZZLE -> {
                            detailViewModel.updatePLayCount(Game.TYPE_15_PUZZLE)
                            openActivity(Main15PuzzleActivity::class.java, canBack = true)
                        }

                        Game.TYPE_FREE_DRAWER -> {
                            detailViewModel.updatePLayCount(Game.TYPE_FREE_DRAWER)
                            openActivity(DrawActivity::class.java, canBack = true)
                        }

                        else -> {
                            if (!this@DetailFragment::updateDialog.isInitialized) {
                                updateDialog = UpdateDialog(activity)
                            }
                            updateDialog.show()
                        }
                    }
                }

            })

        }
    }

    private fun getData() {
        detailViewModel.getDetailGame(typeGame)
    }

    private fun observer() {
        detailViewModel.game.observe(this) {
            binding.game = it
        }
    }

}