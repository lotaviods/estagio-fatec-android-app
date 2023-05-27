package com.github.lotaviods.linkfatec.data.remote.model

import com.google.gson.annotations.SerializedName

data class StudentUser(
    @SerializedName("profile_picture")
    val profilePicture: String?,
    val name: String,
    @SerializedName("course_name")
    val courseName: String?,
    @SerializedName("course_id")
    val courseId: Int = -1
)
