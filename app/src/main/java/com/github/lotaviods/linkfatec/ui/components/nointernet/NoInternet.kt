package com.github.lotaviods.linkfatec.ui.components.nointernet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
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
            Image(
                modifier = Modifier
                    .height(230.dp)
                    .width(230.dp),
                painter =
                painterResource(id = R.drawable.no_internet_connection),
                contentDescription = ""
            )
        }
        Text(
            text = "Aconteceu algum problema \n tente novamente mais tarde",
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