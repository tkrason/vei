package com.vei.repository

import com.mongodb.client.model.Projections
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.Client
import com.vei.model.ClientOption
import org.koin.core.annotation.Singleton

@Singleton
class ClientRepository(
    mongo: Mongo,
) : MongoCrudRepository<Client>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<Client>("client")

    suspend fun getAllClientsOptions() = withCollection {
        find<ClientOption>().projection(Projections.include(Client::name.name))
    }
}
