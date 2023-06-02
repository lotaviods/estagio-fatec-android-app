package com.github.lotaviods.linkfatec.model

import com.google.gson.Gson

data class User(
    val id: Int,
    val name: String,
    val course: Course,
    val ra: String,
    val profilePicture: String? = null,
    val accessToken: String? = null
) {
    companion object {
        fun String.fromJson(): User {
            return Gson().fromJson(this, User::class.java)
        }

        fun User.toJson(): String? {
            return Gson().toJson(this)
        }
    }
}