package com.github.lotaviods.linkfatec.data.remote.serializer

import com.github.lotaviods.linkfatec.data.remote.model.JobNotificationModel
import com.github.lotaviods.linkfatec.data.remote.model.Notification
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class NotificationSerializer: JsonDeserializer<Notification> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Notification {
        val jsonObject = json?.asJsonObject

        return when (val type = jsonObject?.get("notification_type")?.asInt) {
            1 -> context?.deserialize(json, JobNotificationModel::class.java)!!
            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}