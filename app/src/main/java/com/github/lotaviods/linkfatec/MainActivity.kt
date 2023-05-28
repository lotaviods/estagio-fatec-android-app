package com.github.lotaviods.linkfatec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.github.lotaviods.linkfatec.ui.application.navigation.ApplicationNavHost
import com.github.lotaviods.linkfatec.ui.application.viewmodel.ApplicationViewModel
import com.github.lotaviods.linkfatec.ui.application.viewmodel.ApplicationViewModel.ApplicationState
import com.github.lotaviods.linkfatec.ui.theme.MainAppTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        setContent {
            MainAppTheme {
                val lifecycle = LocalLifecycleOwner.current.lifecycle

                val navController = rememberNavController()
                val viewModel: ApplicationViewModel = koinViewModel()
                var startDestination by remember { mutableStateOf("") }

                val stateFlow = remember(viewModel.uiState, lifecycle) {
                    viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                }

                val state = stateFlow.collectAsState(initial = ApplicationState.Loading)

                viewModel.getLoginState()

                Surface(Modifier.fillMaxSize()) {
                    startDestination = when (state.value) {
                        ApplicationState.Loading -> {
                            "loading"
                        }

                        is ApplicationState.Logged -> {
                            "main"
                        }

                        ApplicationState.LoginState -> {
                            "login"
                        }
                    }

                    ApplicationNavHost(navController, startDestination)
                }
            }
        }
    }
}
