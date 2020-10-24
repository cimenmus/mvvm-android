package com.plumbers.mvvm.ui.mission.missions

import androidx.navigation.fragment.findNavController
import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentMissionsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_missions.*

class MissionsFragment: BaseFragment<FragmentMissionsBinding, MissionsViewModel>() {

    override fun getViewModelKey() = MissionsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_missions

    override fun initViews() {
        super.initViews()
        missionsTextView.setOnClickListener {
            val actionDetail =
                MissionsFragmentDirections.actionMissionListToMissionDetails(
                    missionId = 9876,
                    missionName = "Test Mission")
            findNavController().navigate(actionDetail)
        }
    }

}