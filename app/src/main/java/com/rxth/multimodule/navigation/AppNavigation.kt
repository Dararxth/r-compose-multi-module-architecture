package com.rxth.multimodule.navigation

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.rxth.multimodule.SampleScreen
import com.rxth.multimodule.feature.detail.AnotherDetailScreen
import com.rxth.multimodule.feature.detail.DetailScreen
import com.rxth.multimodule.feature.home.presentation.HomeScreen
import com.rxth.multimodule.navigation.tabs.TxnTab

class AppNavigation(
    private val navigator: Navigator,
    private val onSwitchTabs: (NavKey, NavKey?) -> Unit
) {
    fun get(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<AppNavKey.Dashboard> {
                HomeScreen {
                    onSwitchTabs(AppNavKey.Transactions, AppNavKey.Detail(1))
                }
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

            entry<AppNavKey.Detail> { key ->
                DetailScreen(
                    id = key.id,
                    onBack = { navigator.pop() },
                    onNext = {
                        onSwitchTabs(AppNavKey.Transactions, null)
                    }
                )
            }
            entry<AppNavKey.AnotherDetail> { key ->
                AnotherDetailScreen(
                    id = key.id,
                    onBack = { navigator.pop() },
                    tD = {
                        navigator.popAll()
                        onSwitchTabs(AppNavKey.Dashboard, null)
                    }
                )
            }
        }
    }
}
