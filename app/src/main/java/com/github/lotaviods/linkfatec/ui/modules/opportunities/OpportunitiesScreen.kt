@file:OptIn(ExperimentalMaterialApi::class)

package com.github.lotaviods.linkfatec.ui.modules.opportunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.ui.components.dialog.JobDialog
import com.github.lotaviods.linkfatec.ui.components.job.JobPost
import com.github.lotaviods.linkfatec.ui.components.nointernet.NoInternet
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel.UiMessages
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OpportunitiesScreen(opportunitiesViewModel: OpportunitiesViewModel = koinViewModel()) {
    val refreshScope = rememberCoroutineScope()
    val state = opportunitiesViewModel.uiState.collectAsState()
    val isRefreshing = state.value is UiState.Loading

    fun refresh() = refreshScope.launch {
        opportunitiesViewModel.getAvailableJobs()
    }

    LaunchedEffect(opportunitiesViewModel.uiMessages) {
        opportunitiesViewModel.uiMessages.collect { value ->
            if (value is UiMessages.HasFinishedToAppliedToJob) {
                opportunitiesViewModel.closeModalSubscribeJob()
            }
        }
    }

    val error = (state.value as? UiState.Error)?.error

    if (error is ErrorState.InternetConnection) {
        NoInternet()
    }

    val refreshState = rememberPullRefreshState(isRefreshing, ::refresh)

    Box(Modifier.pullRefresh(refreshState)) {

        if (state.value is UiState.NoPostsFound) {
            NoPostsFound()
        }

        if (state.value is UiState.ShowSubscribeModal) {
            val subscribeState = state.value as? UiState.ShowSubscribeModal ?: return

            JobDialog(
                onCancel = {
                    opportunitiesViewModel.closeModalSubscribeJob()
                },
                jobPost = subscribeState.post,
                onApply = {
                    opportunitiesViewModel.applyToJobOffer(subscribeState.post)
                }
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
            val posts = (state.value as? UiState.Loaded)?.posts

            if (posts != null) {
                items(posts.size) { pos ->
                    JobPost(
                        posts[pos],
                        onLikeClicked = { liked ->
                            opportunitiesViewModel.updateLikeCount(posts[pos], liked)
                        },
                        onSubscribeJob = {
                            opportunitiesViewModel.showModalSubscribeJob(posts, pos)
                        },
                        onUnsubscribeJob = {
                            opportunitiesViewModel.unSubscribeJob(posts[pos])
                        }
                    )
                }
            }
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

@Composable
private fun NoPostsFound() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_opportunities_found),
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_opportunities_found),
            style = TextStyle.Default.copy(
                fontSize = TextUnit(22.0F, TextUnitType.Sp),
                color = Color.Gray
            )
        )
    }
}

