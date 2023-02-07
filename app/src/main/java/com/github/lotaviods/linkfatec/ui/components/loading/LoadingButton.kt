package com.github.lotaviods.linkfatec.ui.components.loading

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LoadingText(loading: Boolean) {
    if (loading)
        LoadingCircle()
    else
        Text(
            text = "Login", style = TextStyle.Default.copy(
                color = Color.White
            ), modifier = Modifier.padding(5.dp)
        )
}