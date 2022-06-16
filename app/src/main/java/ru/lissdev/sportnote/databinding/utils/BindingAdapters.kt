package ru.lissdev.sportnote.databinding.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object BindingAdapters {

    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        println(url)
        view.load(url)
    }
}