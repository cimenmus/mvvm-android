package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.common.AppError
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.PersonModel
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    PersonDataSource {

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel> {
        apiService.getPersonDetails(personId = personId).apply {
            return when {
                isSuccess() -> {
                    val person = PersonModel(
                        id = id,
                        name = name,
                        imagePath = imagePath,
                        biography = biography,
                    )
                    Result.Success(person)
                }
                else -> Result.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun savePerson(person: PersonModel) = throw NotImplementedError()
}
