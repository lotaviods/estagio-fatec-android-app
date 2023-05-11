@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.AppliedOffersScreen
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel.AppliedOffersViewModel
import com.github.lotaviods.linkfatec.ui.modules.opportunities.OpportunitiesScreen
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainPager(
    page: Int
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