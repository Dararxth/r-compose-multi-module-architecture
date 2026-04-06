package com.rxth.multimodule.navigation

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.rxth.multimodule.feature.detail.AnotherDetailScreen
import com.rxth.multimodule.feature.detail.DetailScreen
import com.rxth.multimodule.feature.home.HomeScreen

class AppNavigation(private val navigator: Navigator) {
    fun get(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<AppNavKey.Home> {
                com.rxth.multimodule.feature.home.presentation.HomeScreen {
                    navigator.navigate(AppNavKey.Detail(1))
                }
            }
            entry<AppNavKey.Detail> { key ->
                DetailScreen(
                    id = key.id,
                    onBack = { navigator.goBack() },
                    onNext = { navigator.navigate(AppNavKey.AnotherDetail(key.id)) }
                )
            }
            entry<AppNavKey.AnotherDetail> { key ->
                AnotherDetailScreen(
                    id = key.id,
                    onBack = { navigator.goBack() }
                )
            }
        }
    }
}