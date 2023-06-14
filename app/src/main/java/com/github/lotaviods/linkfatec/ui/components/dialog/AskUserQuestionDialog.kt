package com.github.lotaviods.linkfatec.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor

@Composable
fun AskUserQuestionDialog(
    onAccepted: () -> Unit,
    onCanceled: () -> Unit,
    title: String,
    content: String,
    acceptedString: String? = null,
    canceledString: String? = null
) {
    Surface {
        Dialog(onDismissRequest = onCanceled) {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 8.dp,
                backgroundColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White)
                ) {
                    Text(text = title, style = MaterialTheme.typography.h5)
                    Text(text = content, style = MaterialTheme.typography.body2)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        if (canceledString?.isNotEmpty() == true)
                            Button(
                                onClick = { onCanceled() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = ThemeColor.DarkRed,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = canceledString)
                            }

                        if (acceptedString?.isNotEmpty() == true)
                            Button(
                                onClick = { onAccepted() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = ThemeColor.DarkRed,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = acceptedString)
                            }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApplyOrCancelDialogPreview() {
    AskUserQuestionDialog(
        onAccepted = { /*TODO*/ },
        onCanceled = { /*TODO*/ },
        title = "Desistir da vaga",
        content = "Tem certeza que deseja desistir da vaga?",
        acceptedString = "Desistir",
        canceledString = "Cancelar"
    )
}