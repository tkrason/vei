package com.vei.services

import com.vei.model.Person
import com.vei.repository.PeopleRepository
import org.koin.core.annotation.Singleton

@Singleton
class PeopleService(peopleRepository: PeopleRepository) : ModelService<Person>(peopleRepository)
