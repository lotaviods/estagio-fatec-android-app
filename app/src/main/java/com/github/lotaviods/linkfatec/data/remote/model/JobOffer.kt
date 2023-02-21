package com.github.lotaviods.linkfatec.data.remote.model

import com.google.gson.annotations.SerializedName

data class JobOffer (
    val id: Int,
    val description: String,
    @SerializedName("job_experience")
    val jobExperience: String,
    val role: String?,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("company_profile_picture")
    val companyProfilePicture: String?,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("applied_students_count")
    val appliedStudentsCount: Int,
    @SerializedName("promotional_image_url")
    val promotionalImageUrl: String?,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("liked_by")
    val likedBy: List<Int>
)