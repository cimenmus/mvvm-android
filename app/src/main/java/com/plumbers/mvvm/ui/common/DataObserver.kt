package com.plumbers.mvvm.ui.common

import androidx.lifecycle.*
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.ui.common.base.BaseView
import java.lang.ref.WeakReference

class DataObserver<T : DataResult<Any>?>(
    lifecycle: Lifecycle,
    view: BaseView?,
    observer: Observer<T>
) : Observer<T>, LifecycleObserver {

    private var view: WeakReference<BaseView?>?
    private var observer: Observer<T>?

    override fun onChanged(dataResult: T?) {
        view?.get()?.let { v ->
            dataResult?.let {
                when (it) {
                    is DataResult.Loading -> v.showLoading()
                    is DataResult.Success<*> -> v.hideLoading()
                    is DataResult.Error -> v.showError(it.appError)
                }
            }
        }
        observer?.onChanged(dataResult)
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
