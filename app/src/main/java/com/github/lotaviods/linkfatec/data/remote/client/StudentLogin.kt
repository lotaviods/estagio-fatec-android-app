package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.model.Course
import com.google.gson.annotations.SerializedName

data class StudentLogin(
    val data: StudentData,
    val token: TokenData
)

data class StudentData(
    val id: Int,
    val name: String,
    val course: Course,
    val ra: String,
    @SerializedName("profile_picture")
    val profilePicture: String? = null
)

data class TokenData(
    @SerializedName("access_token")
    val accessToken: String
)