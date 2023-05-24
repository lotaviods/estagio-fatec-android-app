@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.AppliedOffersScreen
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel.AppliedOffersViewModel
import com.github.lotaviods.linkfatec.ui.modules.opportunities.OpportunitiesScreen
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainPager(
    page: Int,
) {
    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
    ) {
        when (page) {
            0 -> {
                val viewModel: OpportunitiesViewModel = koinViewModel()

                OpportunitiesScreen(viewModel)
            }

            1 -> {
                val viewModel: AppliedOffersViewModel = koinViewModel()

                AppliedOffersScreen(viewModel)
            }

            2 -> {
                Text("Hello AppliedOffers")
            }
        }
    }
}