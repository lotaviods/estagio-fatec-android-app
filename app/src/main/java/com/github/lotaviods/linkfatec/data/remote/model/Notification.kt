package com.github.lotaviods.linkfatec.data.remote.model

import com.google.gson.annotations.SerializedName

abstract class Notification

data class JobNotificationModel(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("job_title")
    val jobTitle: String,
    @SerializedName("status_changed_date")
    val statusChangedDate: String,
    val approved: Boolean,
    @SerializedName("company_profile_picture")
    val companyProfilePicture: String? = "",
    val location: String
) : Notification()

