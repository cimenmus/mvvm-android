package com.icmen.mvvm.ui.rocket.rockets

import com.icmen.mvvm.R
import com.icmen.mvvm.databinding.FragmentRocketsBinding
import com.icmen.mvvm.ui.common.base.BaseFragment

@Suppress("SpellCheckingInspection")
class RocketsFragment: BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = RocketsFragment()
    }

    override fun getViewModelKey() = RocketsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_rockets

}