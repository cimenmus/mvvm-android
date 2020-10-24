package com.plumbers.mvvm.ui.mission.missiondetails

import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentMissionDetailsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mission_details.*

class MissionDetailsFragment: BaseFragment<FragmentMissionDetailsBinding, MissionDetailsViewModel>() {

    private var missionId: Int = 0

    override fun getViewModelKey() = MissionDetailsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_mission_details

    override fun readDataFromArguments() {
        super.readDataFromArguments()
        arguments?.let {
            val safeArgs = MissionDetailsFragmentArgs.fromBundle(it)
            missionId = safeArgs.missionId
        }
    }

    override fun initViews() {
        super.initViews()
        missionDetailsTextView.text = "Mission Details of: $missionId"
    }

}