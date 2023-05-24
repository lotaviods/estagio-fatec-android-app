package com.github.lotaviods.linkfatec.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.github.lotaviods.linkfatec.model.Post
import com.github.lotaviods.linkfatec.ui.components.loading.LoadingText
import com.github.lotaviods.linkfatec.ui.components.text.BodyCustomText
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor

@Composable
fun JobDialog(
    onApply: () -> Unit,
    onCancel: () -> Unit,
    jobPost: Post?
) {
    var applying by remember { mutableStateOf(false) }

    Surface{
        Dialog(onDismissRequest = onCancel) {
            Card(
                shape = MaterialTheme.shapes.medium,
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White)
                ) {
                    Text(
                        text = "Aplicar vaga para: ${jobPost?.role}",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    BodyCustomText(text = jobPost?.description, modifier = Modifier.height(300.dp))

                    jobPost?.promotionalImageUrl?.let { PromotionalImage(it) }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onCancel,
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ThemeColor.DarkRed,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Cancelar",
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                applying = true
                                onApply()
                            },
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ThemeColor.DarkRed,
                                contentColor = Color.White
                            )
                        ) {
                            LoadingText(
                                loading = applying,
                                text = "Aplicar vaga",
                                textStyle = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PromotionalImage(url: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(10.dp)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = url,
            contentDescription = "promotional_job_image",
            contentScale = ContentScale.Fit
        )
    }
}