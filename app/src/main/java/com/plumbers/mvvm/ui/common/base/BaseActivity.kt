package com.plumbers.mvvm.ui.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB: ViewDataBinding>: AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        //binding = DataBindingUtil.setContentView(this, getLayoutRes())
        readDataFromIntent()
        initViews()
        initLogic()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    open fun initViews() {}

    open fun readDataFromIntent() {}

    open fun initLogic() {}
}