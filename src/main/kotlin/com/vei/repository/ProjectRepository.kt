package com.vei.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.Project
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class ProjectRepository(mongo: Mongo) : MongoCrudRepository<Project>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<Project>("project")

    suspend fun getAllProjectsForClient(objectId: ObjectId) = withCollection {
        find(Filters.eq(Project::belongsToClient.name, objectId))
    }
}
