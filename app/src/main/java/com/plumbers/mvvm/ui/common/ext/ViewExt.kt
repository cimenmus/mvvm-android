package com.plumbers.mvvm.ui.common.ext

import android.view.View

fun View?.setVisibility(
    isVisible: Boolean,
    setAsGone: Boolean = true
) {
    this?.let {
        visibility =
            when {
                isVisible -> View.VISIBLE
                setAsGone -> View.GONE
                else -> View.INVISIBLE
            }
    }
}

fun View?.toggleVisibility(setAsGone: Boolean = true) {
    this?.let {
        val nextVisibility = when (it.visibility) {
            View.GONE -> View.VISIBLE
            View.INVISIBLE -> View.VISIBLE
            else -> if (setAsGone) View.GONE else View.INVISIBLE
        }
        it.visibility = nextVisibility
    }
}
