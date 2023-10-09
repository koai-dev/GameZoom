package com.koai.gamezoom.ui.main

import android.Manifest
import android.app.ActionBar
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.game.ActionBlockPuzzle
import com.game.UtilsAwv
import com.game.jumpboy.Action
import com.game.jumpboy.MainActivity
import com.game.knife.ActionKnife
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.base.adapter.BaseListAdapter
import com.koai.base.ui.activity.BaseActivity
import com.koai.gamezoom.GameApplication
import com.koai.gamezoom.account.AccountUtils
import com.koai.gamezoom.api.ApiFirebase
import com.koai.gamezoom.databinding.ActivityMainBinding
import com.koai.gamezoom.db.viewmodel.GameViewModel
import com.koai.gamezoom.db.viewmodel.GameViewModelFactory
import com.koai.gamezoom.models.Game
import com.koai.gamezoom.models.User
import com.koai.gamezoom.models.base.BaseResponse
import com.koai.gamezoom.ui.customView.BaseLoadingView
import com.koai.gamezoom.ui.detail.DetailFragment
import com.koai.gamezoom.ui.login.LoginDialog
import com.koai.gamezoom.ui.main.adapter.GameAdapter
import com.koai.gamezoom.ui.main.adapter.SlideAdapter
import com.koai.gamezoom.ui.main.viewmodel.MainViewModel
import com.koai.gamezoom.ui.rank.RankFragment
import com.koai.gamezoom.utils.AdmobUtils
import com.koai.gamezoom.utils.GameUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(), Action,  ActionBlockPuzzle,
    MainViewModel.LoginCallBack, ActionKnife {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GameAdapter
    private val gameViewModel: GameViewModel by viewModels { GameViewModelFactory((application as GameApplication).repository) }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var slideAdapter: SlideAdapter
    private var isRunSlide = false
    private lateinit var loginDialog: LoginDialog
    lateinit var callbackManager: CallbackManager
    lateinit var loginCallBack: MainViewModel.LoginCallBack
    lateinit var oneTapClient: SignInClient
    private var countLoginClick = 0
    var statusBarHeight = 0
    var bottomNavigationHeight = 0
    private var isLoadAdmobFirst = MutableLiveData<Boolean>()

    val registerForActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            countLoginClick += 1
            try {
                if (it?.data != null) {
                    val credential = oneTapClient.getSignInCredentialFromIntent(it.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        Firebase.auth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                idToken,
                                null
                            )
                        ).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                loginCallBack.onLoginSuccess()
                            } else {
                                loginCallBack.onLoginFail(
                                    BaseResponse<User>(
                                        message = task.exception?.message ?: "Unknown Error."
                                    )
                                )
                            }
                        }
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> if (countLoginClick == 2) {
                        loginCallBack.onLoginFail(
                            BaseResponse<User>(
                                message = "Too many action cancel to login."
                            )
                        )
                    }

                    else -> loginCallBack.onLoginFail(
                        BaseResponse<User>(
                            message = e.message ?: "Error not define."
                        )
                    )
                }
            }
        }

    override fun getBindingView() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?, binding: ViewBinding) {
        this.binding = binding as ActivityMainBinding
        MobileAds.initialize(this) {}
        AdmobUtils.setAdmob(this, isLoadAdmobFirst)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val paramsTop = binding.pointTop.layoutParams as ViewGroup.MarginLayoutParams
            statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            bottomNavigationHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            paramsTop.setMargins(
                0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).top, 0, 0
            )
            binding.pointTop.layoutParams = paramsTop

            val paramsBot = binding.pointBot.layoutParams as ViewGroup.MarginLayoutParams
            paramsBot.setMargins(
                0, 0, 0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            binding.pointBot.layoutParams = paramsBot
            insets
        }
        binding.root.post {
            binding.btnRank.setWidthHeight(binding.root.measuredHeight)
        }
        loginCallBack = this
        callbackManager = CallbackManager.Factory.create()
        oneTapClient = Identity.getSignInClient(this)
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    loginCallBack.onLoginFail(BaseResponse<User>(message = "Login canceled."))
                }

                override fun onError(error: FacebookException) {
                    loginCallBack.onLoginFail(BaseResponse<User>(message = error.message))
                }

                override fun onSuccess(result: LoginResult) {
                    val accessToken = AccessToken.getCurrentAccessToken()
                    if (accessToken != null && !accessToken.isExpired) {
                        val credential = FacebookAuthProvider.getCredential(accessToken.token)
                        Firebase.auth.currentUser?.linkWithCredential(credential)
                        Firebase.auth.signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    loginCallBack.onLoginSuccess()
                                } else if (task.exception?.message?.contains("An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.") == true) {
                                    mainViewModel.loginByGg(
                                        oneTapClient,
                                        registerForActivityResultLauncher,
                                        loginCallBack
                                    )
                                } else {
                                    loginCallBack.onLoginFail(
                                        BaseResponse<User>(
                                            message = task.exception?.message ?: "Unknown Error."
                                        )
                                    )
                                }
                            }
                    }
                }

            })

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        MainActivity.action = this
        UtilsAwv.actionBlockPuzzle = this
        com.game.knife.UtilsAwv.actionKnife = this

        setClick()
        setSlide()
        setRcv()
        getData()
        observer()

        askNotificationPermission()
    }

    override fun onStart() {
        super.onStart()
        AccountUtils.auth()
    }

    override fun getLoadingView(): View {
        return BaseLoadingView(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
        }
    }

    private fun setClick() {
        binding.btnRank.setOnClickListener {
            if (AccountUtils.isLogin()) {
                openRankScreen()
            } else {
                showLoginDialog()
            }
        }
    }

    private fun showLoginDialog() {
        if (this::loginDialog.isInitialized) {
            loginDialog.show()
        } else {
            loginDialog = LoginDialog(this, object : LoginDialog.Action {
                override fun onLoginFb() {
                    toggleProgressLoading(true)
                    mainViewModel.loginByFb(this@MainActivity)
                }

                override fun onLoginGg() {
                    toggleProgressLoading(true)
                    mainViewModel.loginByGg(
                        oneTapClient,
                        registerForActivityResultLauncher,
                        loginCallBack
                    )
                }

            })
            loginDialog.show()
        }
    }

    private fun openRankScreen() {
        addFragment(RankFragment(), binding.containerMain.id)
    }

    private fun observer() {
        isLoadAdmobFirst.observe(this){
            if (it){
                AdmobUtils.showAdmob(this)
            }
        }
        mainViewModel.listGame.observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
                slideAdapter.submitList(it)
                GameUtils.listGame = it
            }
        }
        gameViewModel.allGameLocal.observe(this) {
            GameUtils.farmPuzzleBestScore = it
                ?.filter { game -> game.type == Game.TYPE_FARM_PUZZLE }
                ?.sortedByDescending { game2 -> game2.score }
                ?.firstOrNull()
                ?.score ?: 0
            GameUtils.jumpPrincessBestScore = it
                ?.filter { game -> game.type == Game.TYPE_JUMP_PRINCESS }
                ?.sortedByDescending { game2 -> game2.score }
                ?.firstOrNull()
                ?.score ?: 0
            GameUtils.knifeBestScore = it
                ?.filter { game -> game.type == Game.TYPE_KNIFE }
                ?.sortedByDescending { game2 -> game2.score }
                ?.firstOrNull()
                ?.score ?: 0
        }
    }

    private fun setRcv() {
        adapter = GameAdapter()
        adapter.listener = object : BaseListAdapter.Action<Game> {
            override fun click(position: Int, data: Game, code: Int) {
                addFragment(DetailFragment().apply { typeGame= data.productId?:Game.TYPE_FARM_PUZZLE }, binding.containerMain.id)
            }

        }
        binding.rcvListGame.adapter = adapter
    }

    private fun setSlide() {
        slideAdapter = SlideAdapter()
        binding.slide.adapter = slideAdapter
        binding.slide.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (!isRunSlide) {
                    isRunSlide = true
                    lifecycleScope.launch(Dispatchers.Main) {
                        delay(3000)
                        if (binding.slide.currentItem < slideAdapter.currentList.size - 1) {
                            binding.slide.setCurrentItem(binding.slide.currentItem + 1, true)
                        } else {
                            binding.slide.setCurrentItem(0, true)
                        }
                    }.invokeOnCompletion {
                        isRunSlide = false
                    }
                }
            }
        })

    }

    private fun getData() {
        mainViewModel.getAllGames()
    }

    override fun getScore(score: Int) {
        val game = Game(
            type = Game.TYPE_FARM_PUZZLE,
            name = "Farm Puzzle",
            score = score,
            userId = AccountUtils.user?.userId,
            userImage = AccountUtils.user?.image,
            userName = AccountUtils.user?.name,
            thumb = GameUtils.listGame?.firstOrNull { game2 -> game2.type == Game.TYPE_FARM_PUZZLE }?.thumb
        )
        if (score > GameUtils.jumpPrincessBestScore && AccountUtils.isLogin()) {
            ApiFirebase.addGame(Game.TYPE_JUMP_PRINCESS, game, AccountUtils.user!!.userId)
        }
        gameViewModel.insert(
            game
        )
    }

    override fun getScoreBlockPuzzle(score: Int) {
        val game = Game(
            type = Game.TYPE_FARM_PUZZLE,
            name = "Farm Puzzle",
            score = score,
            userId = AccountUtils.user?.userId,
            userImage = AccountUtils.user?.image,
            userName = AccountUtils.user?.name,
            thumb = GameUtils.listGame?.firstOrNull { game2 -> game2.type == Game.TYPE_FARM_PUZZLE }?.thumb
        )
        if (score > GameUtils.farmPuzzleBestScore && AccountUtils.isLogin()) {
            ApiFirebase.addGame(Game.TYPE_FARM_PUZZLE, game, AccountUtils.user!!.userId)
        }
        gameViewModel.insert(
            game
        )
    }

    override fun onLoginSuccess(user: BaseResponse<User>?) {
        AccountUtils.auth()
        toggleProgressLoading(false)
        binding.btnRank.performClick()
    }

    override fun onLoginFail(user: BaseResponse<User>?) {
        if (user?.message != null) {
            Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
        }
        toggleProgressLoading(false)
    }

    override fun getLastScoreKnife(score: Int) {
        val game = Game(
            type = Game.TYPE_KNIFE,
            name = "Knife",
            score = score,
            userId = AccountUtils.user?.userId,
            userImage = AccountUtils.user?.image,
            userName = AccountUtils.user?.name,
            thumb = GameUtils.listGame?.firstOrNull { game2 -> game2.type == Game.TYPE_KNIFE }?.thumb
        )
        if (score > GameUtils.knifeBestScore && AccountUtils.isLogin()) {
            ApiFirebase.addGame(Game.TYPE_KNIFE, game, AccountUtils.user!!.userId!!)
        }
        gameViewModel.insert(
            game
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Can post notifications.
        } else {
            // Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}