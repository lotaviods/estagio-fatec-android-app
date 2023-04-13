package com.github.lotaviods.linkfatec.ui.modules.profile

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.model.User
import com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel.ProfileViewModel
import com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel.ProfileViewModel.*
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User?,
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val filePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    if (uri != null) {
                        sendProfileResume(context, uri, viewModel)
                    }
                }
            }
        }

    LaunchedEffect(viewModel.uiState) {
        viewModel.uiState.collectLatest {
            if (it is UiState.Error) {
                Toast.makeText(context, "Ocorreu algum erro ao enviar o currículo", Toast.LENGTH_SHORT).show()
            }
            if(it is UiState.Success) {
                Toast.makeText(context, "Currículo enviado com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.my_profile), color = Color.White) },
            backgroundColor = ThemeColor.Red,
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            },
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            val activity = (LocalContext.current as? Activity)
            Card(
                modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                elevation = 5.dp
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_placeholder),
                        contentDescription = "PROFILE",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(150.dp)
                            .padding(5.dp)
                            .clip(CircleShape)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Row {
                        Text(text = "Nome:")
                        Text(
                            text = user?.name ?: "",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp)
                        )
                    }
                    Row {
                        Text(text = "Turma:")
                        Text(
                            text = user?.course?.name ?: "",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp)
                        )

                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                viewModel.logoutUser(user)

                                activity?.recreate()
                            }, colors = ButtonDefaults.buttonColors(
                                backgroundColor = ThemeColor.Red
                            )
                        ) {
                            Text(text = "Deslogar", color = Color.White)
                        }
                        Button(
                            onClick = {
                                filePicker.launch(arrayOf("application/pdf"))
                            }, colors = ButtonDefaults.buttonColors(
                                backgroundColor = ThemeColor.Red
                            )
                        ) {
                            Text(text = "Enviar currículo", color = Color.White)

                            Icon(
                                modifier = Modifier.padding(start = 10.dp),
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Upload photo",
                                tint = Color.White
                            )
                        }
                    }

                }
            }
        }

    }
}

suspend fun sendProfileResume(context: Context, uri: Uri, viewModel: ProfileViewModel) {
    withContext(Dispatchers.IO) {
        val inputStream = context.contentResolver.openInputStream(uri)
        try {
            val pdfBytes = inputStream?.readBytes()

            viewModel.sendProfileResume(pdfBytes)
        } catch (e: Exception) {
            Log.e(TAG, "sendProfileResume: ", e)
        } finally {
            inputStream?.close()
        }
    }
}

private const val TAG = "ProfileScreen"