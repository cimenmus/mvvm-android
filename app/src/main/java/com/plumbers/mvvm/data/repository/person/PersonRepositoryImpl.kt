package com.plumbers.mvvm.data.repository.person

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.util.NetworkUtils
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.source.person.PersonDataSource
import com.plumbers.mvvm.di.qualifier.LocalPersonDataSource
import com.plumbers.mvvm.di.qualifier.RemotePersonDataSource
import javax.inject.Inject

class PersonRepositoryImpl
@Inject constructor(
    private val networkUtils: NetworkUtils,
    @RemotePersonDataSource private val personRemoteDataSource: PersonDataSource,
    @LocalPersonDataSource private val personLocalDataSource: PersonDataSource
) : PersonRepository {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> {
        val networkResult = personRemoteDataSource.getPersonDetails(personId = personId)
        return if (networkResult is Result.Success) {
            savePerson(person = networkResult.data)
            networkResult
        } else {
            personLocalDataSource.getPersonDetails(personId = personId)
        }
    }

    override suspend fun savePerson(person: PersonModel) {
        personLocalDataSource.savePerson(person = person)
    }
}
