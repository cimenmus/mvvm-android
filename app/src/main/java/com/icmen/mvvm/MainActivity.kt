package com.icmen.mvvm

import com.icmen.mvvm.databinding.ActivityMainBinding
import com.icmen.mvvm.ui.MainViewModel
import com.icmen.mvvm.ui.common.base.BaseActivity
import com.icmen.mvvm.ui.rocket.rockets.RocketsFragment

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