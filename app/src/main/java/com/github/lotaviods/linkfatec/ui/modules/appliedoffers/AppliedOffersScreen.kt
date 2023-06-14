@file:OptIn(ExperimentalMaterialApi::class)

package com.github.lotaviods.linkfatec.ui.modules.appliedoffers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.ui.components.dialog.AskUserQuestionDialog
import com.github.lotaviods.linkfatec.ui.components.dialog.JobDialog
import com.github.lotaviods.linkfatec.ui.components.job.JobPost
import com.github.lotaviods.linkfatec.ui.components.nointernet.NoInternet
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel.AppliedOffersViewModel
import kotlinx.coroutines.launch

@Composable
fun AppliedOffersScreen(modifier: Modifier, appliedOffersViewModel: AppliedOffersViewModel) {
    val refreshScope = rememberCoroutineScope()
    val state = appliedOffersViewModel.uiState.collectAsState()
    val isRefreshing =
        (state.value is AppliedOffersViewModel.UiState.Loading
                || state.value is AppliedOffersViewModel.UiState.Reloading)

    fun refresh() = refreshScope.launch {
        appliedOffersViewModel.loadAppliedJobs()
    }

    val refreshState = rememberPullRefreshState(isRefreshing, ::refresh)

    Box(modifier.pullRefresh(refreshState)) {

        if (state.value is AppliedOffersViewModel.UiState.LoadedEmpty) {
            NoSubscribedJobs()
        }

        if (state.value is AppliedOffersViewModel.UiState.Error) {
            NoInternet()
        }

        if (state.value is AppliedOffersViewModel.UiState.ShowSubscribeModal) {
            val uiState = (state.value as? AppliedOffersViewModel.UiState.ShowSubscribeModal)
            uiState?.let {
                JobDialog(
                    onCancel = {
                        appliedOffersViewModel.closeJobDetailsModal()
                    },
                    jobPost = uiState.post,
                    onApply = {}
                )
            }
        }
        if (state.value is AppliedOffersViewModel.UiState.ShowUserCancelApplication) {
            AskUserQuestionDialog(
                onAccepted = {
                    (state.value as? AppliedOffersViewModel.UiState.ShowUserCancelApplication)?.post?.let {
                        appliedOffersViewModel.unsubscribeJobPost(
                            post = it
                        )
                        appliedOffersViewModel.closeUserUnsubscribeConfirmation()
                    }
                },
                onCanceled = { appliedOffersViewModel.closeUserUnsubscribeConfirmation() },
                title = "Desistir da vaga",
                content = "Tem certeza que deseja desistir da vaga?",
                acceptedString = "Desistir",
                canceledString = "Cancelar"
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
            val posts = (state.value as? AppliedOffersViewModel.UiState.Loaded)?.posts

            if (posts != null) {
                items(posts.size) { pos ->
                    JobPost(
                        post = posts[pos],
                        onOpenDetailsJob = {
                            appliedOffersViewModel.openJobDetailsModal(post = posts[pos], posts)
                        },
                        onLikeClicked = {
                            appliedOffersViewModel.updateLikeCount(posts[pos], it)
                        },
                        onUnsubscribeJob = {
                            appliedOffersViewModel.showUserUnsubscribeConfirmation(
                                posts[pos],
                                posts
                            )
                        },
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
private fun NoSubscribedJobs() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_opportunities_applied),
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_opportunities_applied),
            style = TextStyle.Default.copy(
                fontSize = TextUnit(22.0F, TextUnitType.Sp),
                color = Color.Gray
            )
        )
    }
}


@Composable
@Preview
private fun AppliedOffersScreenPreview() {

}