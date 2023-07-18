package com.vei.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.Person
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class PeopleRepository(
    mongo: Mongo,
) : MongoCrudRepository<Person>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<Person>("people")

    suspend fun findManyByIds(ids: List<ObjectId>) = findManyAsFlow {
        Filters.`in`(Mongo.MONGO_ID_FIELD, ids)
    }
}
