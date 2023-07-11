package com.vei.services

import com.vei.model.User
import com.vei.repository.UserRepository
import org.koin.core.annotation.Singleton

@Singleton
class UserService(userRepository: UserRepository) : ModelService<User>(userRepository)
