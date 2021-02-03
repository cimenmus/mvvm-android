package com.plumbers.mvvm.data.repository.person

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.PersonModel

interface PersonRepository {

    suspend fun getPersonDetails(personId: Int): Result<PersonModel>

    suspend fun savePerson(person: PersonModel)
}