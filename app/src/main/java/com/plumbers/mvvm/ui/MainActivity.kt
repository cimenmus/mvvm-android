package com.plumbers.mvvm.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.ActivityMainBinding
import com.plumbers.mvvm.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onSupportNavigateUp() =
        getNavController().navigateUp() || super.onSupportNavigateUp()

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        return navHostFragment.navController
    }
}
