package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.PersonModel

interface PersonDataSource {

    suspend fun getPersonDetails(personId: Int): DataResult<PersonModel>

    suspend fun savePerson(person: PersonModel)
}
