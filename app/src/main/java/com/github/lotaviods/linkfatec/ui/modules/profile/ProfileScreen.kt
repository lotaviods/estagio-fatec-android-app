package com.github.lotaviods.linkfatec.ui.modules.profile

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Send
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
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
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    val user: User = state.value.user
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val resumeFilePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    if (uri != null) {
                        sendProfileResume(context, uri, viewModel)
                    }
                }
            }
        }

    val profilePicturePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    if (uri != null) {
                        sendProfilePicture(context, uri, viewModel)
                    }
                }
            }
        }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collectLatest {
            if (it is UiEvent.Error) {
                Toast.makeText(context, "Ocorreu algum erro ao enviar as informações", Toast.LENGTH_SHORT).show()
            }
            if(it is UiEvent.Success) {
                Toast.makeText(context, "Enviado com sucesso", Toast.LENGTH_SHORT).show()
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(150.dp)
                            .padding(5.dp)
                            .clickable {
                                profilePicturePicker.launch(
                                    "image/*"
                                )
                            }
                    ) {
                        // TODO: Make request to update profile picture
                        var painter: AsyncImagePainter? = null

                        if(user.profilePicture != null)
                            painter = rememberAsyncImagePainter(model = user.profilePicture)

                        Image(
                            painter = painter ?: painterResource(id = R.drawable.profile_placeholder),
                            contentDescription = "PROFILE",
                            modifier = Modifier
                                .size(150.dp)
                                .padding(5.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.BottomEnd)
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = "Change photo",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable(onClick = {
                                        profilePicturePicker.launch(
                                            "image/*"
                                        )
                                    })
                            )
                        }
                    }
                    Row {
                        Text(text = "Nome:")
                        Text(
                            text = user.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp)
                        )
                    }
                    Row {
                        Text(text = "Turma:")
                        Text(
                            text = user.course.name,
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
                                resumeFilePicker.launch(arrayOf("application/pdf"))
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

fun sendProfilePicture(context: Context, uri: Uri, viewModel: ProfileViewModel) {
    val inputStream = context.contentResolver.openInputStream(uri)
    try {
        val bytes = inputStream?.readBytes()

        viewModel.sendProfilePicture(bytes)
    } catch (e: Exception) {
        Log.e(TAG, "sendProfileResume: ", e)
    } finally {
        inputStream?.close()
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