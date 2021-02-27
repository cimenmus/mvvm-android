package com.plumbers.mvvm.ui.common.base

import com.plumbers.mvvm.data.AppError

/**
 * Every fragment need to be implemented from [BaseView]
 * to observe a LiveData with [DataObserver]
 * to handle show/hide loading and error dialogs automatically
 *
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: AppError)
}
