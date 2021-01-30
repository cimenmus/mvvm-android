package com.plumbers.mvvm.ui.common.ext

import android.widget.ImageView
import com.plumbers.mvvm.common.Constants
import com.squareup.picasso.Picasso

fun ImageView?.loadTmdbImage(url: String?) {
    this?.let {
        url?.let {
            load(url = "${Constants.Api.IMAGE_PREFIX}$url")
        }
    }
}

fun ImageView?.load(url: String?) {
    this?.let { imageView ->
        url?.let {
            Picasso.get()
                .load(it)
                .into(imageView)
        }
    }
}
