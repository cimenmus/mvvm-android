package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.ErrorType
import com.plumbers.mvvm.common.AppError
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.db.PersonDao
import com.plumbers.mvvm.data.model.PersonModel
import javax.inject.Inject

class PersonLocalDataSource
@Inject constructor(
    private val personDao: PersonDao,
) : PersonDataSource {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> {
        personDao.getPerson(personId = personId)?.let {
            return Result.Success(it)
        } ?: run {
            val error = AppError(
                type = ErrorType.DB_ITEM_NOT_FOUND,
                message = "Person not found on database"
            )
            return Result.Error(error)
        }
    }

    override suspend fun savePerson(person: PersonModel) {
        personDao.add(person = person)
    }
}