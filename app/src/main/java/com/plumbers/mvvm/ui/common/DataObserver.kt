package com.plumbers.mvvm.ui.common

import androidx.lifecycle.*
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.ui.common.base.BaseView
import java.lang.ref.WeakReference

/**
 * Observers a [LiveData]
 * Used with a [BaseView] instance
 * shows loading indicator when [Result.Loading] by calling [showLoading] method in [BaseView]
 * hides loading indicator when [Result.Success] or [Result.Error]  by calling [hideLoading] method in [BaseView]
 * shows error dialog when [Result.Error] by calling [showError] method in [BaseView]
 */
class DataObserver<T : Result<Any>?>(
    lifecycle: Lifecycle,
    view: BaseView?,
    observer: Observer<T>
) : Observer<T>, LifecycleObserver {

    private var view: WeakReference<BaseView?>?
    private var observer: Observer<T>?

    override fun onChanged(result: T?) {
        view?.get()?.let { v ->
            result?.let {
                when (it) {
                    is Result.Loading -> v.showLoading()
                    is Result.Success<*> -> v.hideLoading()
                    is Result.Error -> {
                        v.hideLoading()
                        v.showError(it.appError)
                    }
                }
            }
        }
        observer?.onChanged(result)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        view = null
        observer = null
    }

    init {
        this.view = WeakReference<BaseView?>(view)
        this.observer = observer
        lifecycle.addObserver(this)
    }
}
