package com.github.lotaviods.linkfatec.ui.application.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.lotaviods.linkfatec.model.User.Companion.fromJson
import com.github.lotaviods.linkfatec.ui.login.LoginScreen
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import com.github.lotaviods.linkfatec.ui.mainscreen.MainScreen
import com.github.lotaviods.linkfatec.ui.profile.ProfileScreen
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
        composable("profile/{profile-arg}", arguments = listOf(
            navArgument("profile-arg") {
                type = NavType.StringType
            }
        )) {
            val userJson = it.arguments?.getString("profile-arg")

            ProfileScreen(
                user = userJson?.fromJson(), navController = navController
            )
        }
        composable("loading") {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White)
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
