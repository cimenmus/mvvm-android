package com.icmen.mvvm.ui.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<VB: ViewDataBinding, VM: ViewModel>: AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelKey()]
        setContentView(getLayoutRes())
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        readDataFromIntent()
        initViews()
        initLogic()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModelKey(): Class<VM>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    open fun initViews() {}

    open fun readDataFromIntent() {}

    open fun initLogic() {}
}