package com.github.lotaviods.linkfatec.ui.components.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.ui.components.loading.LoadingText
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import com.github.lotaviods.linkfatec.ui.util.closeVirtualKeyboard
import com.github.lotaviods.linkfatec.ui.util.ShowTrailingIconIfTextIsNotEmpty

@Composable
fun LoginForm(
    modifier: Modifier,
    onLoginClick: (user: String, password: String) -> Unit,
    loading: Boolean
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        var userTextInput by rememberSaveable { mutableStateOf("") }
        var passwordTextInput by rememberSaveable { mutableStateOf("") }

        Column {

            Text(text = stringResource(R.string.email_).uppercase())

            TextField(
                value = userTextInput,
                onValueChange = {
                    userTextInput = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(BorderStroke(25.dp, Color.Transparent)),
                trailingIcon = {
                    ShowTrailingIconIfTextIsNotEmpty(userTextInput, icon = {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }, onClick = { userTextInput = "" })
                },
                keyboardActions = KeyboardActions(onDone = {
                    closeVirtualKeyboard(context)
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            Text(text = stringResource(R.string.password).uppercase())
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            TextField(
                value = passwordTextInput,
                onValueChange = {
                    passwordTextInput = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    ShowTrailingIconIfTextIsNotEmpty(
                        passwordTextInput,
                        { passwordVisible = !passwordVisible }) {
                        val icon = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation =
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.border(BorderStroke(25.dp, Color.Transparent)),
                keyboardActions = KeyboardActions(onDone = {
                    closeVirtualKeyboard(context)
                    onLoginClick(userTextInput, passwordTextInput)
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ).copy()
            )
        }

        Button(
            onClick = {
                closeVirtualKeyboard(context)
                onLoginClick(userTextInput, passwordTextInput)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ThemeColor.Red
            ),
            modifier = Modifier.padding(top = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            LoadingText(loading, "Login")
        }
    }
}