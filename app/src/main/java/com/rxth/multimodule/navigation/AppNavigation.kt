package com.rxth.multimodule.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.rxth.multimodule.SampleScreen
import com.rxth.multimodule.feature.home.presentation.DetailScreen
import com.rxth.multimodule.feature.home.presentation.HomeScreen
import com.rxth.multimodule.navigation.tabs.TxnTab

class AppNavigation(
    private val navigator: Navigator,
    private val shareTransitionScope: SharedTransitionScope,
    private val onSwitchTabs: (NavKey, NavKey?) -> Unit,
) {
    fun get(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<AppNavKey.Dashboard> {
                HomeScreen(
                    shareTransitionScope = shareTransitionScope,
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    onNavigateToDetails = { movie, listId ->
                        navigator.navigate(AppNavKey.Detail(movie, listId))
                    }
                )
            }
            entry<AppNavKey.Transactions> {
                TxnTab {
                    navigator.navigate(AppNavKey.AnotherDetail(1))
                }
            }
            entry<AppNavKey.Reports> {
                SampleScreen("Reports Content")
            }
            entry<AppNavKey.Profile> {
                SampleScreen("Profile Content")
            }

            entry<AppNavKey.Detail> {
                DetailScreen(
                    movie = it.movie,
                    listId = it.listId,
                    shareTransitionScope = shareTransitionScope,
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                ) {
                    navigator.pop()
                }
            }
        }
    }
}