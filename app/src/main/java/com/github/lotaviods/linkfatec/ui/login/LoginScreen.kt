@file:OptIn(ExperimentalUnitApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.github.lotaviods.linkfatec.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.ui.components.login.LoginForm
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel.LoginUiState
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import org.koin.androidx.compose.inject

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, onSuccess: () -> Unit) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = ThemeColor.Red,
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.LightGray,
                        style = TextStyle.Default.copy(
                            fontSize = TextUnit(20F, TextUnitType.Sp)
                        )
                    )
                }
            )
        }) { paddingValues ->
        LoginScreenContent(Modifier.padding(paddingValues), onSuccess, scaffoldState, viewModel)
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier,
    onSuccess: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: LoginScreenViewModel
) {
    val userRepository: UserRepository by inject()
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsState(initial = LoginUiState.Initial)

    if (state.value is LoginUiState.Error) {
        LaunchedEffect(viewModel.uiState.value) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.generic_error_message)
            )
        }
    }

    if (state.value is LoginUiState.Success) {
        LaunchedEffect(viewModel.uiState.value) {
            userRepository.saveUser((state.value as LoginUiState.Success).profile)
            onSuccess()
        }
    }

    Column(modifier) {
        Box(
            modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.fatec_garca_logo),
                contentDescription = "CPS_LOGO",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(45.dp),
                contentScale = ContentScale.Fit
            )

        }

        LoginForm(modifier, viewModel::login, state.value is LoginUiState.Loading)
    }
}