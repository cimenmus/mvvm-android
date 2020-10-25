package com.plumbers.mvvm.ui.rocket.rockets

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.plumbers.mvvm.R
import com.plumbers.mvvm.data.model.RocketModel
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

    override fun initLogic() {
        super.initLogic()
        viewModel.getRockets()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.rocketsLiveData.observe(viewLifecycleOwner, Observer {
            print(it)
        })
    }

}