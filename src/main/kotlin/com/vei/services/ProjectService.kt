package com.vei.services

import com.vei.model.Project
import com.vei.repository.ProjectRepository
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class ProjectService(private val projectRepository: ProjectRepository) : ModelService<Project>(projectRepository) {

    suspend fun getAllProjectsForClient(clientId: ObjectId) = projectRepository.getAllProjectsForClient(clientId)
}
