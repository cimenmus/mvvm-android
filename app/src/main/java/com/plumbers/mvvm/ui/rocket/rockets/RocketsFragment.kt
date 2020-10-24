package com.plumbers.mvvm.ui.rocket.rockets

import androidx.navigation.fragment.findNavController
import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentRocketsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_rockets.*

class RocketsFragment: BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {

    override fun getViewModelKey() = RocketsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_rockets

    override fun initViews() {
        super.initViews()
        rocketsTextView.setOnClickListener {
            val actionDetail = RocketsFragmentDirections.actionRocketListToRocketDetails(rocketId = 1234)
            findNavController().navigate(actionDetail)
        }
    }

}