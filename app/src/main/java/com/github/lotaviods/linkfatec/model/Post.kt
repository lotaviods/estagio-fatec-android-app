package com.github.lotaviods.linkfatec.model

data class Post(
    val id: Int,
    val companyName: String,
    val companyProfilePicture: String,
    val role: String,
    val description: String,
    val promotionalImageUrl: String?,
    var likeCount: Int,
    val liked: Boolean = false,
    var subscribedCount: Int,
    var subscribed: Boolean = false,
    val createdAt: Long
)