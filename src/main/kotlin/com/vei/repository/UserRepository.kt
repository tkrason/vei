package com.vei.repository

import com.vei.application.Mongo
import com.vei.model.User
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.koin.core.annotation.Singleton

@Singleton
class UserRepository(mongo: Mongo) : MongoCrudRepository<User>(mongo, "ktor-sample") {
    override fun MongoDatabase.selectRepositoryCollection(): MongoCollection<User> {
        return getCollection<User>("user")
    }
}
