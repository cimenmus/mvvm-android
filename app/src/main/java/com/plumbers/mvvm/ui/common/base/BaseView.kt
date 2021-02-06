package com.plumbers.mvvm.ui.common.base

import com.plumbers.mvvm.data.AppError

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: AppError)
}