package com.koai.gamezoom.bindingUtils

import android.annotation.SuppressLint
import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.google.android.material.imageview.ShapeableImageView
import com.koai.gamezoom.BuildConfig
import com.koai.gamezoom.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

@SuppressLint("SetTextI18n")
@BindingAdapter("app_version")
fun appVersion(textView: TextView, txt: String) {
    textView.text = "$txt ${BuildConfig.VERSION_NAME}"
}

@BindingAdapter("thumb")
fun setThumb(shapeableImageView: ShapeableImageView, url: String?) {
    if (url != null) {
        Glide.with(shapeableImageView).load(url).error(R.drawable.ic_item_default)
            .into(shapeableImageView)
    }
}

@BindingAdapter("blur")
fun setBlur(shapeableImageView: ShapeableImageView, url: String?) {
    if (url != null) {
        Glide.with(shapeableImageView).load(url).apply(bitmapTransform(BlurTransformation(25, 3)))
            .error(R.drawable.ic_item_default).into(shapeableImageView)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("playCount")
fun setPlayCounter(textView: TextView, count: Long) {
    if (count < 1000) {
        textView.text = "$count"
    } else if (count in 1000..999999) {
        textView.text = "${(count / 100.0).roundToInt() / 10}K"
    } else if (count in 1000000..999999999) {
        textView.text = "${(count / 100000.0).roundToInt() / 10}M"
    } else if (count in 1000000000..1000000000000) {
        textView.text = "${(count / 100000000.0).roundToInt() / 10}G"
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun setDate(textView: TextView, timestamp: Long) {
    val formatter = SimpleDateFormat("dd:MM:yyyy HH:mm:ss")
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    textView.text = formatter.format(calendar.time)
}

@BindingAdapter("text_html")
fun setTextHtml(textView: TextView, txt: String?) {
    if (txt != null) {
        textView.text = Html.fromHtml(txt.toString())
    } else {
        textView.text = ""
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("price")
fun setPriceConverter(textView: TextView, pr: Int?) {
    var price = "Price: "
    val prS = pr.toString()
    if (pr != null && pr > 0) {
        if (prS.length > 6) {
            price += prS.substring(0, prS.length - 6) + "." + prS.substring(
                prS.length - 6,
                prS.length - 3
            ) + "." + prS.substring(prS.length - 3, prS.length)
        } else if (prS.length > 3) {
            price += prS.substring(0, prS.length - 3) + "." + prS.substring(
                prS.length - 3,
                prS.length
            )
        }
        textView.text = "$price VND"

    } else {
        price = "Free"
        textView.text = price
    }
}