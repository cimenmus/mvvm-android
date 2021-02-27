package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.data.result.DatabaseResource
import com.plumbers.mvvm.data.db.PersonDao
import com.plumbers.mvvm.data.model.PersonModel
import javax.inject.Inject
import com.plumbers.mvvm.data.result.Result

class PersonLocalDataSource
@Inject constructor(
    private val personDao: PersonDao,
) : PersonDataSource {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> =
        object : DatabaseResource<PersonModel>() {
            override suspend fun load(): PersonModel? {
                return personDao.getPerson(personId = personId)
            }
        }.execute()

    override suspend fun savePerson(person: PersonModel) =
        personDao.add(person = person)
}
