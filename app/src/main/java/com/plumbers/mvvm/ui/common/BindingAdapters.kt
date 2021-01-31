package com.plumbers.mvvm.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.plumbers.mvvm.ui.common.ext.load
import com.plumbers.mvvm.ui.common.ext.loadTmdbImage

@BindingAdapter("tmdbImageUrl")
fun loadTmdbImage(imageView: ImageView, url: String?) {
    imageView.loadTmdbImage(url = url)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.load(url = url)
}