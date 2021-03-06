package com.plumbers.mvvm.data.repository.person

import com.plumbers.mvvm.data.util.NetworkUtils
import com.plumbers.mvvm.data.result.NetworkBoundResult
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.source.person.PersonDataSource
import com.plumbers.mvvm.di.qualifier.LocalPersonDataSource
import com.plumbers.mvvm.di.qualifier.RemotePersonDataSource
import javax.inject.Inject
import com.plumbers.mvvm.data.result.Result

class PersonRepositoryImpl
@Inject constructor(
    private val networkUtils: NetworkUtils,
    @RemotePersonDataSource private val personRemoteDataSource: PersonDataSource,
    @LocalPersonDataSource private val personLocalDataSource: PersonDataSource
) : PersonRepository {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> =
        object : NetworkBoundResult<PersonModel>() {

            override fun isOnline(): Boolean = networkUtils.isNetworkAvailable()

            override suspend fun loadFromNetwork(): Result<PersonModel> =
                personRemoteDataSource.getPersonDetails(personId = personId)

            override suspend fun loadFromDb(): Result<PersonModel> =
                personLocalDataSource.getPersonDetails(personId = personId)

            override suspend fun saveNetworkResult(data: PersonModel) =
                savePerson(person = data)
        }.execute()

    override suspend fun savePerson(person: PersonModel) {
        personLocalDataSource.savePerson(person = person)
    }
}
