@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.AppliedOffersScreen
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel.AppliedOffersViewModel
import com.github.lotaviods.linkfatec.ui.modules.opportunities.OpportunitiesScreen
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import org.koin.androidx.compose.koinViewModel

@OptIn(FlowPreview::class)
@Composable
fun MainPager(
    page: Int,
    pagerState: PagerState,
) {
    val opportunitiesViewModel: OpportunitiesViewModel = koinViewModel()
    val appliedOffersViewModel: AppliedOffersViewModel = koinViewModel()
    var currentPage by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .debounce(500)
            .drop(1) // Skip the initial emission
            .collectLatest { offsetPage ->
                when (offsetPage) {
                    0 -> {
                        if (currentPage == 0) return@collectLatest

                        opportunitiesViewModel.getAvailableJobs()
                        currentPage = 0
                    }

                    1 -> {
                        if (currentPage == 1) return@collectLatest
                        Log.e(TAG, "MainPager enters: $currentPage")
                        appliedOffersViewModel.reloadOrLoadAppliedJob()
                        currentPage = 1
                    }
                }
            }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        when (page) {
            0 -> {
                OpportunitiesScreen(opportunitiesViewModel, pagerState)
            }

            1 -> {
                AppliedOffersScreen(appliedOffersViewModel)
            }

            2 -> {
                Text("Hello AppliedOffers")
            }
        }
    }
}

private const val TAG = "MainPager"