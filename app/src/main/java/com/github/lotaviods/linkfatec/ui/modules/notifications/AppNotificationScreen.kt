@file:OptIn(ExperimentalMaterialApi::class)

package com.github.lotaviods.linkfatec.ui.modules.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.ui.components.nointernet.NoInternet
import com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel.AppNotificationsViewModel
import com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel.AppNotificationsViewModel.*
import kotlinx.coroutines.launch

@Composable
fun AppNotificationScreen(viewModel: AppNotificationsViewModel) {
    val refreshScope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsState()
    val isRefreshing =
        (state.value is UiState.Loading)

    fun refresh() = refreshScope.launch {

    }

    val refreshState = rememberPullRefreshState(isRefreshing, ::refresh)

    Box(Modifier.pullRefresh(refreshState)) {

        if (state.value is UiState.LoadedEmpty) {
            // TODO: Empty component
        }

        if (state.value is UiState.Error) {
            NoInternet()
        }


        LazyColumn(Modifier.fillMaxSize()) {
            (state.value as? UiState.Loaded)
            // TODO: Set items
//            items {  ->
//
//            }

        }

        var height by remember {
            mutableStateOf(0.dp)
        }

        val localDensity = LocalDensity.current

        Box(modifier = Modifier
            .onGloballyPositioned {
                height = with(localDensity) { it.size.height.toDp() }
            }
            .align(Alignment.TopCenter)
            .offset(y = height - (height.times(0.1f)))
        ) {
            PullRefreshIndicator(
                isRefreshing,
                refreshState
            )
        }
    }
}