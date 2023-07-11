package com.vei.repository

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.Client
import org.koin.core.annotation.Singleton

@Singleton
class ClientRepository(
    mongo: Mongo,
) : MongoCrudRepository<Client>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<Client>("client")
}
