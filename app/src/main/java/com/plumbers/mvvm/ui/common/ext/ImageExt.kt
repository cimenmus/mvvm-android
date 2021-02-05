package com.plumbers.mvvm.ui.common.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.plumbers.mvvm.BuildConfig
import com.plumbers.mvvm.R
import com.squareup.picasso.Picasso

fun ImageView?.loadTmdbImage(url: String?, placeHolder: Drawable? = null) {
    this?.let {
        url?.let {
            load(url = "${BuildConfig.API_IMAGE_PREFIX}$url", placeHolder = placeHolder)
        }
    }
}

fun ImageView?.load(
    url: String?,
    withPlaceholder: Boolean = true,
    withError: Boolean = true,
    placeHolder: Drawable? = null
) {
    this?.let { imageView ->
        url?.let {
            val requestCreator = Picasso.get().load(it)
            val placeHolderDrawable: Drawable = placeHolder ?: ContextCompat.getDrawable(context, R.drawable.placeholder_small)!!
            if (withPlaceholder) {
                requestCreator.placeholder(placeHolderDrawable)
            }
            if (withError) {
                requestCreator.error(placeHolderDrawable)
            }
            requestCreator.into(imageView)
        }
    }
}
