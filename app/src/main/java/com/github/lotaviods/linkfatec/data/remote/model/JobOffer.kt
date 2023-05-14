package com.github.lotaviods.linkfatec.data.remote.model

import androidx.compose.ui.text.intl.Locale
import com.github.lotaviods.linkfatec.model.Post
import com.github.lotaviods.linkfatec.model.User
import com.google.gson.annotations.SerializedName
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat

data class JobOffer(
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
    val likedBy: List<Int>,
    @SerializedName("subscribed_by")
    val subscribedBy: List<Int>,
    @SerializedName("created_at")
    val createdAt: String
) {

    fun toPost(user: User): Post {
        return Post(
            this.id,
            this.companyName,
            companyProfilePicture ?: "",
            this.role ?: "",
            this.description,
            this.promotionalImageUrl,
            this.likeCount,
            this.likedBy.contains(user.id),
            this.appliedStudentsCount,
            this.subscribedBy.contains(user.id),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(createdAt).time
        )
    }

}