package com.github.lotaviods.linkfatec.data.repository.interfaces

import com.github.lotaviods.linkfatec.model.User

interface UserRepository {
    fun getUser(): User

    fun saveUser(user: User)

    suspend fun getUpdatedUserInformation(): User

    fun deleteUser(user: User)
}