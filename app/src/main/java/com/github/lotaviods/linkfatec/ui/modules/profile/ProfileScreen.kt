package com.github.lotaviods.linkfatec.ui.modules.profile

import android.app.Activity
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.Course
import com.github.lotaviods.linkfatec.model.User
import com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel.ProfileViewModel
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User?,
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel()
) {
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
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        // TODO: Add viewModel
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
                    }

                }
            }
        }

    }
}


// Preview
@Composable
@Preview
private fun defaultPreview() {
    ProfileScreen(
        Modifier,
        User(
            1,
            "Luiz Otávio da Silva Carvalho",
            Course(1, "Analise e desenvolvimento de sistemas"),
            "284913918568341"
        ), rememberNavController(),
        ProfileViewModel(object : UserRepository{
            override fun getUser(): User {
                TODO("Not yet implemented")
            }

            override fun saveUser(user: User) {
                TODO("Not yet implemented")
            }

            override fun deleteUser(user: User) {
                TODO("Not yet implemented")
            }
        })
    )
}