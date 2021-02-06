package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.PersonModel
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    PersonDataSource {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> {
        return apiService.getPersonDetails(personId = personId)
    }

    override suspend fun savePerson(person: PersonModel) = throw NotImplementedError()
}
