package com.plumbers.mvvm.ui.rocket.rockets

import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentRocketsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment

@Suppress("SpellCheckingInspection")
class RocketsFragment: BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = RocketsFragment()
    }

    override fun getViewModelKey() = RocketsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_rockets

}