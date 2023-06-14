package com.github.lotaviods.linkfatec.ui.components.nointernet

import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.viewinterop.AndroidView
import com.github.lotaviods.linkfatec.R

@Composable
fun NoInternet(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = modifier,
                factory = { context: Context ->
                    ImageView(context).apply {
                        setImageResource(R.drawable.no_internet_connection)
                    }
                },
                update = {
                    it.setImageResource(R.drawable.no_internet_connection)
                }
            )
        }
        Text(
            text = stringResource(R.string.internet_problem_description),
            textAlign = TextAlign.Center,
            style = TextStyle.Default.copy(
                fontSize = TextUnit(
                    24.0F, TextUnitType.Sp
                ),
                color = Color.Gray
            )
        )
    }
}


@Composable
@Preview
private fun NoInternetPreview() {
    NoInternet()
}