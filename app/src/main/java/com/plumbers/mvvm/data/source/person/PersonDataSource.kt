package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.PersonModel

interface PersonDataSource {

    suspend fun getPersonDetails(personId: Int): Result<PersonModel>

    suspend fun savePerson(person: PersonModel)
}
