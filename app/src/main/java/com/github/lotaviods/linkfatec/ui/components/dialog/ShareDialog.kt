package com.github.lotaviods.linkfatec.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.lotaviods.linkfatec.model.Post
import com.github.lotaviods.linkfatec.ui.components.loading.LoadingText

@Composable
fun ShareDialog(
    onApply: () -> Unit,
    onCancel: () -> Unit,
    jobPost: Post?
) {
    var applying by remember { mutableStateOf(false) }

    Surface {
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
                    Text(text = "${jobPost?.description}", style = MaterialTheme.typography.body1)
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onCancel,
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Red,
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
                                backgroundColor = Color.Red,
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
