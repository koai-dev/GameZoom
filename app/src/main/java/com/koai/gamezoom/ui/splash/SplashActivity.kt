package com.koai.gamezoom.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.koai.base.ui.activity.BaseActivity
import com.koai.gamezoom.databinding.ActivitySplashBinding
import com.koai.gamezoom.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override fun getBindingView() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?, binding: ViewBinding) {
        binding as ActivitySplashBinding
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val paramsTop =
                binding.pointTop.layoutParams as ViewGroup.MarginLayoutParams
            paramsTop.setMargins(
                0,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                0,
                0
            )
            binding.pointTop.layoutParams = paramsTop

            val paramsBot =
                binding.pointBot.layoutParams as ViewGroup.MarginLayoutParams
            paramsBot.setMargins(
                0,
                0,
                0,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            binding.pointBot.layoutParams = paramsBot
            insets
        }
        binding.root.postDelayed({
            openActivity(MainActivity::class.java, canBack = false)
        }, 1500)
    }


}