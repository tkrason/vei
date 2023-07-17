package com.vei.repository

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.Person
import org.koin.core.annotation.Singleton

@Singleton
class PeopleRepository(
    mongo: Mongo,
) : MongoCrudRepository<Person>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<Person>("people")
}
