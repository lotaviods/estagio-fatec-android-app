@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPagerNavigator(
    modifier: Modifier, pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    BottomNavigation(
        modifier, backgroundColor = ThemeColor.Red
    ) {

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            selected = pagerState.currentPage == 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = stringResource(R.string.vagas_de_estagio),
                    tint = if (pagerState.currentPage == 0) Color.White else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            label = {
                Text(
                    stringResource(R.string.vagas_de_estagio),
                    color = if (pagerState.currentPage == 0) Color.White else Color.Unspecified
                )
            },
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            selected = pagerState.currentPage == 1,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = stringResource(R.string.vagas_aplicadas),
                    tint = if (pagerState.currentPage == 1) Color.White else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            label = {
                Text(
                    stringResource(R.string.vagas_aplicadas),
                    color = if (pagerState.currentPage == 1) Color.White else Color.Unspecified
                )
            },
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            selected = pagerState.currentPage == 2,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(2)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = stringResource(R.string.notificacoes),
                    tint = if (pagerState.currentPage == 2) Color.White else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            label = {
                Text(
                    stringResource(R.string.notificacoes),
                    color = if (pagerState.currentPage == 2) Color.White else Color.Unspecified
                )
            },
        )
    }
}