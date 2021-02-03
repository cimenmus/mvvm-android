package com.plumbers.mvvm.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.update
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.domain.GetPersonDetailsResultUseCase
import com.plumbers.mvvm.domain.PersonDetailParameter
import kotlinx.coroutines.launch

class PersonDetailsViewModel
@ViewModelInject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsResultUseCase
) : ViewModel() {

    val personDetailsLiveData = MutableLiveData<Result<PersonModel>>()

    fun getPersonDetails(personId: Int) {
        personDetailsLiveData.value = Result.Loading
        viewModelScope.launch {
            val params = PersonDetailParameter(personId = personId)
            getPersonDetailsUseCase(parameters = params).update(liveData = personDetailsLiveData)
        }
    }
}
