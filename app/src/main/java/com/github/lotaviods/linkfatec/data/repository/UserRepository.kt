package com.github.lotaviods.linkfatec.data.repository

import com.github.lotaviods.linkfatec.model.User

interface UserRepository {
    fun getUser(): User

    fun saveUser(user: User)

    fun deleteUser(user: User)
}