package com.github.lotaviods.linkfatec.helper

import java.util.concurrent.TimeUnit


object TimeHelper {

    fun getElapsedTimeString(timestamp: Long): String {
        val elapsed = System.currentTimeMillis() - timestamp
        val years = TimeUnit.MILLISECONDS.toDays(elapsed) / 365
        val days = TimeUnit.MILLISECONDS.toDays(elapsed)
        val hours = TimeUnit.MILLISECONDS.toHours(elapsed) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60

        return when {
            years > 1 -> "$years anos atrás"
            years == 1.toLong() -> "$years ano atrás"
            days > 0 -> "${days}d atrás"
            hours > 0 -> "${hours}h atrás"
            minutes > 0 -> "${minutes}m atrás"
            else -> "Agora"
        }
    }
}