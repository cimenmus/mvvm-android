package com.plumbers.mvvm.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.plumbers.mvvm.di.Injectable
import com.plumbers.mvvm.ui.common.autoCleared
import javax.inject.Inject

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel>: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM
    var binding by autoCleared<VB>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding =  DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelKey()]
        readDataFromArguments()
        initViews()
        initObservers()
        initLogic()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModelKey(): Class<VM>

    open fun readDataFromArguments() {}

    open fun initViews() {}

    open fun initObservers() {}

    open fun initLogic() {}

}