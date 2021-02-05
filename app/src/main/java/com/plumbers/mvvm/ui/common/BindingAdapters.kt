package com.plumbers.mvvm.ui.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.plumbers.mvvm.ui.common.ext.load
import com.plumbers.mvvm.ui.common.ext.loadTmdbImage

@BindingAdapter("tmdbImageUrl")
fun loadTmdbImage(imageView: ImageView, url: String?) {
    loadTmdbImage(imageView = imageView, url = url, placeHolder = null)
}

@BindingAdapter("tmdbImageUrl", "placeHolder")
fun loadTmdbImage(imageView: ImageView, url: String?, placeHolder: Drawable?) {
    imageView.loadTmdbImage(url = url, placeHolder = placeHolder)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.load(url = url)
}