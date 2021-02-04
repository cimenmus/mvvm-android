package com.plumbers.mvvm.ui.common

import androidx.lifecycle.*
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.ui.common.base.BaseView
import java.lang.ref.WeakReference

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
