package com.plumbers.mvvm.ui.rocket.rocketdetail

import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentRocketDetailsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_rocket_details.*

class RocketDetailsFragment: BaseFragment<FragmentRocketDetailsBinding, RocketDetailsViewModel>() {

    private var rocketId: Int = 0

    override fun getViewModelKey() = RocketDetailsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_rocket_details

    override fun readDataFromArguments() {
        super.readDataFromArguments()
        arguments?.let {
            val safeArgs = RocketDetailsFragmentArgs.fromBundle(it)
            rocketId = safeArgs.rocketId
        }
    }

    override fun initViews() {
        super.initViews()
        rocketDetailsTextView.text = "Rocket Details of: $rocketId"
    }

}