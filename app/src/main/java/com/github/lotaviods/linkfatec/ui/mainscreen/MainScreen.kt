@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.User.Companion.toJson
import com.github.lotaviods.linkfatec.ui.mainscreen.navigation.MainPager
import com.github.lotaviods.linkfatec.ui.mainscreen.navigation.MainPagerNavigator
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import org.koin.androidx.compose.inject

@Composable
fun MainScreen(navController: NavHostController) {
    val userRepository: UserRepository by inject()

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = ThemeColor.Red, content = {
                Row {

                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()) {
                        Text(
                            style = TextStyle.Default.copy(
                                fontSize = 20.sp
                            ),
                            text = "Fatec estágios", color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(5.dp)
                            .selectable(selected = false,
                                indication = rememberRipple(
                                    bounded = false,
                                    color = LocalContentColor.current
                                ),
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    navController.navigate(
                                        "profile/${
                                            userRepository
                                                .getUser()
                                                .toJson()
                                        }"
                                    )
                                })

                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = stringResource(
                                R.string.perfil
                            ),
                            tint = Color.White
                        )
                        Text(
                            text = stringResource(
                                R.string.perfil
                            ), color = Color.White
                        )
                    }

                }
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
            MainPager(it)
        }
    }
}

