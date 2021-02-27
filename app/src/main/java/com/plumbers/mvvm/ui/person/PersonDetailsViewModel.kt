package com.plumbers.mvvm.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.update
import com.plumbers.mvvm.domain.person.GetPersonDetailsUseCase
import com.plumbers.mvvm.domain.person.PersonDetailParameter
import kotlinx.coroutines.launch

class PersonDetailsViewModel
@ViewModelInject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase
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
