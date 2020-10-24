package com.plumbers.mvvm

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.plumbers.mvvm.databinding.ActivityMainBinding
import com.plumbers.mvvm.ui.MainViewModel
import com.plumbers.mvvm.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun getViewModelKey() = MainViewModel::class.java

    override fun initViews() {
        super.initViews()
        initActionBar()
        initBottomNavigation()
    }

    private fun initActionBar(){
        val appBarConfiguration = AppBarConfiguration(getNavController().graph)
        setupActionBarWithNavController(getNavController(), appBarConfiguration)
    }

    private fun initBottomNavigation() =
        bottomNavigationView.setupWithNavController(getNavController())

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onSupportNavigateUp()
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        return navHostFragment.navController
    }

}