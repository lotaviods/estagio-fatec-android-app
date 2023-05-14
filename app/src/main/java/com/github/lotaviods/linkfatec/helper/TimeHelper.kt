package com.github.lotaviods.linkfatec.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.concurrent.TimeUnit


object TimeHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getElapsedTimeString(timestamp: Long): String {
        val elapsed = System.currentTimeMillis() - timestamp
        val days = TimeUnit.MILLISECONDS.toDays(elapsed)
        val hours = TimeUnit.MILLISECONDS.toHours(elapsed) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60

        return when {
            days > 0 -> "${days}d atrás"
            hours > 0 -> "${hours}h atrás"
            minutes > 0 -> "${minutes}m atrás"
            else -> "Agora"
        }
    }
}