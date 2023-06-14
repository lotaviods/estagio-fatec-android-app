package com.github.lotaviods.linkfatec.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.ui.login.LoginScreen
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import com.github.lotaviods.linkfatec.ui.modules.mainscreen.MainScreen
import com.github.lotaviods.linkfatec.ui.modules.profile.ProfileScreen
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApplicationNavHost(navController: NavHostController, startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            val loginScreenViewModel: LoginScreenViewModel = koinViewModel()

            LoginScreen(loginScreenViewModel) {
                navController.navigate("main") {
                    popUpTo("main") { inclusive = true }
                }
            }
        }
        composable("main") {
            MainScreen(navController)
        }
        composable("profile/") {
            ProfileScreen(
                Modifier
                    .background(Color(0xFFE6E6E6))
                    .fillMaxSize(),
                navController = navController
            )
        }
        composable("loading") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cps_gov_background_image),
                    contentDescription = "CPS_LOGO",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillHeight
                )
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = ThemeColor.Red
                    )
                }
            }
        }
    }
}
