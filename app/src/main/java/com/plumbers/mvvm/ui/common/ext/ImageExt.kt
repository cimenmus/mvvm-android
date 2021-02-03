package com.plumbers.mvvm.ui.common.ext

import android.widget.ImageView
import com.plumbers.mvvm.BuildConfig
import com.plumbers.mvvm.R
import com.squareup.picasso.Picasso

fun ImageView?.loadTmdbImage(url: String?) {
    this?.let {
        url?.let {
            load(url = "${BuildConfig.API_IMAGE_PREFIX}$url")
        }
    }
}

fun ImageView?.load(
    url: String?,
    withPlaceholder: Boolean = true,
    withError: Boolean = true
) {
    this?.let { imageView ->
        url?.let {
            val requestCreator = Picasso.get().load(it)
            if (withPlaceholder) {
                requestCreator.placeholder(R.drawable.placeholder)
            }
            if (withError) {
                requestCreator.error(R.drawable.placeholder)
            }
            requestCreator.into(imageView)
        }
    }
}