package com.vei.services

import com.vei.model.Person
import com.vei.repository.PeopleRepository
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class PeopleService(private val peopleRepository: PeopleRepository) : ModelService<Person>(peopleRepository) {

    suspend fun findManyByIds(peopleIds: List<ObjectId>) = peopleRepository.findManyByIds(peopleIds)
}
