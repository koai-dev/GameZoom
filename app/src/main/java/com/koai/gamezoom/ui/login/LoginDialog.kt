package com.koai.gamezoom.ui.login

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentDialog
import androidx.databinding.DataBindingUtil
import com.koai.gamezoom.R
import com.koai.gamezoom.databinding.DialogLoginBinding

class LoginDialog(context: Context, val action: Action? = null) :
    ComponentDialog(context, R.style.MyDialog) {
    private lateinit var binding: DialogLoginBinding

    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window!!.attributes.windowAnimations = R.style.DialogAnimation
        this.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        this.setCanceledOnTouchOutside(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_login, null, false)
        setContentView(binding.root)
        binding.btnFb.setOnClickListener {
            action?.onLoginFb()
            dismiss()
        }
        binding.btnGg.setOnClickListener {
            action?.onLoginGg()
            dismiss()
        }
    }

    interface Action {
        fun onLoginFb()
        fun onLoginGg()
    }
}