package com.plumbers.mvvm.ui.person

import com.plumbers.mvvm.R
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.data
import com.plumbers.mvvm.common.data.succeeded
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.databinding.FragmentPersonDetailsBinding
import com.plumbers.mvvm.ui.common.DataObserver
import com.plumbers.mvvm.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

@AndroidEntryPoint
class PersonDetailsFragment : BaseFragment<FragmentPersonDetailsBinding, PersonDetailsViewModel>() {

    private var personId: Int = 0
    private lateinit var personName: String
    private var person: PersonModel? = null

    override fun getViewModelKey() = PersonDetailsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_person_details

    override fun getTitle() = personName

    override fun readDataFromArguments() {
        super.readDataFromArguments()
        arguments?.let {
            val safeArgs = PersonDetailsFragmentArgs.fromBundle(it)
            personId = safeArgs.personId
            personName = safeArgs.personName
        }
    }

    override fun initLogic() {
        super.initLogic()
        if (person == null) {
            viewModel.getPersonDetails(personId = personId)
        }
    }

    override fun initObservers() {
        super.initObservers()
        val dataObserver = DataObserver<Result<PersonModel>>(
            lifecycle = lifecycle,
            view = this
        ) {
            if (it.succeeded) {
                person = it.data!!
                binding.person = person
            }
        }
        viewModel.personDetailsLiveData.observe(viewLifecycleOwner, dataObserver)
    }
}
