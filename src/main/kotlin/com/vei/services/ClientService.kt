package com.vei.services

import com.vei.model.Client
import com.vei.model.ClientDeleteResponse
import com.vei.repository.ClientRepository
import com.vei.repository.ProjectRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class ClientService(
    private val clientRepository: ClientRepository,
    private val projectRepository: ProjectRepository,
) : ModelService<Client>(clientRepository) {

    suspend fun getAllClientsOptions() = clientRepository.getAllClientsOptions()

    suspend fun deleteClient(clientId: ObjectId) = coroutineScope {
        val deletedProjectsCount = async { projectRepository.deleteAllClientProjects(clientId) }
        val deletedClientsCount = async { deleteOneById(clientId.toHexString()) }

        ClientDeleteResponse(deletedClientsCount.await(), deletedProjectsCount.await())
    }
}
