@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.app.Application
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation.MainPager
import com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation.MainPagerNavigator
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import org.koin.androidx.compose.inject

@Composable
fun MainScreen(navController: NavHostController) {
    val application = LocalContext.current.applicationContext as Application
    val userRepository: UserRepository by inject()

    val postNotificationResult =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                application.registerPush()
            }
        }
    SideEffect {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            postNotificationResult.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = ThemeColor.Red, content = {
                AppBarContent(navController, userRepository)
            })
        }) { paddingValues ->
        MainScreen(Modifier.padding(paddingValues))
    }
}

@Composable
internal fun MainScreen(modifier: Modifier) {
    Column {
        val pagerState = rememberPagerState(0)

        MainPagerNavigator(
            modifier, pagerState
        )

        HorizontalPager(
            pageCount = 3,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            MainPager(it, pagerState)
        }
    }
}

@Composable
private fun AppBarContent(navController: NavHostController, userRepository: UserRepository) {
    Row {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 5.dp)
        ) {
            Text(
                style = TextStyle.Default.copy(
                    fontSize = 20.sp
                ),
                text = "Fatec estágios", color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ProfilePictureButton(
            modifier = Modifier
                .padding(5.dp)
                .selectable(
                    selected = false,
                    indication = rememberRipple(
                        bounded = false,
                        color = LocalContentColor.current
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        navController.navigate(
                            "profile/"
                        )
                    },
                )
        )
    }
}

@Composable
private fun ProfilePictureButton(modifier: Modifier) {
    ConstraintLayout(modifier) {
        val (icon, text) = createRefs()

        Icon(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(icon) {
                    bottom.linkTo(text.top)
                },
            painter = painterResource(id = R.drawable.profile),
            contentDescription = stringResource(
                R.string.perfil
            ),
            tint = Color.White
        )
        Text(
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(parent.bottom)
            },
            text = stringResource(
                R.string.perfil
            ), color = Color.White
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}