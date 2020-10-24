package com.plumbers.mvvm

import com.plumbers.mvvm.databinding.ActivityMainBinding
import com.plumbers.mvvm.ui.MainViewModel
import com.plumbers.mvvm.ui.common.base.BaseActivity
import com.plumbers.mvvm.ui.rocket.rockets.RocketsFragment

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun getViewModelKey() = MainViewModel::class.java

    override fun initViews() {
        super.initViews()
        showFragment()
    }

    private fun showFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerLayout, RocketsFragment.newInstance())
            .commit()
    }
}