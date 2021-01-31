package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.common.AppError
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.PersonModel
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    PersonDataSource {

    override suspend fun getPersonDetails(personId: Int): DataResult<PersonModel> {
        apiService.getPersonDetails(personId = personId).apply {
            return when {
                isSuccess() -> {
                    val person = PersonModel(
                        id = id,
                        name = name,
                        imagePath = imagePath,
                        biography = biography,
                    )
                    DataResult.Success(person)
                }
                else -> DataResult.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun savePerson(person: PersonModel) = throw NotImplementedError()
}
