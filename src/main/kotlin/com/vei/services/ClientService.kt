package com.vei.services

import com.vei.model.Client
import com.vei.repository.ClientRepository
import org.koin.core.annotation.Singleton

@Singleton
class ClientService(clientRepository: ClientRepository) : ModelService<Client>(clientRepository)
