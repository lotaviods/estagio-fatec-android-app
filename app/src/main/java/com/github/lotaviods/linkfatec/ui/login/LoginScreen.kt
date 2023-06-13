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
import com.github.lotaviods.linkfatec.MainActivity
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.app.Application
import com.github.lotaviods.linkfatec.ui.components.login.LoginForm
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel.LoginUiState
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor

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
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsState(initial = LoginUiState.Initial)

    if (state.value is LoginUiState.Error) {
        LaunchedEffect(state) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.generic_error_message)
            )
        }
    }

    if (state.value is LoginUiState.Declined) {
        LaunchedEffect(state) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.login_invalid)
            )
        }
    }

    if (state.value is LoginUiState.Success) {
        LaunchedEffect(state) {
            ((context as MainActivity).application as? Application)?.registerPush()
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
                painter = painterResource(id = R.drawable.horizontal_logogov_cps),
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