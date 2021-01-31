package com.plumbers.mvvm.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
import kotlinx.coroutines.launch

class PersonDetailsViewModel
@ViewModelInject constructor(private val personRepository: PersonRepository): ViewModel() {

    val personDetailsLiveData = MutableLiveData<DataResult<PersonModel>>()

    fun getPersonDetails(personId: Int) {
        personDetailsLiveData.value = DataResult.Loading
        viewModelScope.launch {
            personRepository.getPersonDetails(personId = personId).apply {
                personDetailsLiveData.postValue(this)
            }
        }
    }
}