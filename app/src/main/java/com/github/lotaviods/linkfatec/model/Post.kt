package com.github.lotaviods.linkfatec.model

data class Post(
    val id: Int,
    val companyName: String,
    val companyProfilePicture: String,
    val role: String,
    val description: String,
    val promotionalImageUrl: String?,
    val likeCount: Int
)