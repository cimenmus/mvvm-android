package com.plumbers.mvvm.domain

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
import com.plumbers.mvvm.di.qualifier.IoDispatcher
import com.plumbers.mvvm.domain.base.ResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetPersonDetailsResultUseCase @Inject constructor(
    private val personRepository: PersonRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : ResultUseCase<PersonDetailParameter, PersonModel>(ioDispatcher) {

    override suspend fun execute(parameters: PersonDetailParameter): Result<PersonModel> =
        personRepository.getPersonDetails(personId = parameters.personId)
}

data class PersonDetailParameter(val personId: Int)