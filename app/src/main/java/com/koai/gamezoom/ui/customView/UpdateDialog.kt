package com.koai.gamezoom.ui.customView

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentDialog
import com.koai.gamezoom.R
import com.koai.gamezoom.databinding.DialogUpdateBinding

class UpdateDialog(context: Context) :
    ComponentDialog(context, R.style.MyDialog) {
    private lateinit var binding: DialogUpdateBinding

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
        binding = DialogUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnUpdate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.koai.gamezoom"))
            context.startActivity(intent)
            dismiss()
        }
    }
}