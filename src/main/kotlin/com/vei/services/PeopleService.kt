package com.vei.services

import com.vei.model.Person
import com.vei.model.SlotOption
import com.vei.repository.PeopleRepository
import org.koin.core.annotation.Singleton

@Singleton
class PeopleService(private val peopleRepository: PeopleRepository) : ModelService<Person>(peopleRepository) {

    suspend fun findManyByIds(slotOptions: List<SlotOption>) = peopleRepository.findManyByIds(slotOptions.map { it.personId })
}
