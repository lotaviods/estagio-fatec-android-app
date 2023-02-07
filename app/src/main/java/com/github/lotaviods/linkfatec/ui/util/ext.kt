package com.github.lotaviods.linkfatec.ui.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat


@Composable
fun showTrailingIconIfTextIsNotEmpty(text: String, onClick: () -> Unit, icon: @Composable () -> Unit) {
    if (text.isNotEmpty()) {
        IconButton(onClick = { onClick() }) {
            icon()
        }
    }
}

fun closeVirtualKeyboard(context: Context) {
    val inputMethodManager =
        ContextCompat.getSystemService(context, InputMethodManager::class.java)

    val view = (context as Activity).findViewById<View>(android.R.id.content)

    inputMethodManager?.hideSoftInputFromWindow(
        view.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}
