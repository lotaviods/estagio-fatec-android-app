@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.mainscreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import kotlinx.coroutines.launch

@Composable
fun MainPagerNavigator(
    modifier: Modifier, pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    BottomNavigation(
        modifier, backgroundColor = ThemeColor.Red
    ) {

        MainNavItem(
            Modifier.weight(1f),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = stringResource(R.string.vagas_de_estagio),
                    tint = it
                )
            },
            text = {
                Text(stringResource(R.string.vagas), color = it)
            },
            selected = pagerState.currentPage == 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            })

        MainNavItem(
            modifier = Modifier.weight(1f),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = stringResource(R.string.vagas_aplicadas),
                    tint = it
                )
            },
            text = {
                Text(stringResource(R.string.vagas_aplicadas), color = it)
            },
            interactionSource = remember {
                MutableInteractionSource()
            },
            selected =  pagerState.currentPage == 1,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            })

        MainNavItem(
            Modifier.weight(1f),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = stringResource(R.string.notificacoes),
                    tint = it
                )
            },
            text = {
                Text(stringResource(R.string.notificacoes), color = it)
            },
            selected =  pagerState.currentPage == 2,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(2)
                }
            })
    }
}

@Composable
fun MainNavItem(
    modifier: Modifier,
    icon: @Composable (tint: Color) -> Unit,
    text: @Composable (tint: Color) -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    selectedContentColor: Color = LocalContentColor.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val ripple = rememberRipple(bounded = false, color = selectedContentColor)

    Row(
        modifier = modifier
            .fillMaxHeight()
            .selectable(selected = selected,
                indication = ripple,
                interactionSource = interactionSource,
                onClick = {
                    onClick()
                })
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val iconTint = if (selected) Color.White else Color.DarkGray
        val textTint = if (selected) Color.White else Color.Unspecified

        icon(iconTint)

        text(textTint)
    }
}