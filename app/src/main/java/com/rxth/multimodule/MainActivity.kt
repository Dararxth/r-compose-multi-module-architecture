package com.rxth.multimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.rxth.multimodule.core.ui.theme.AppMaterialTheme
import com.rxth.multimodule.navigation.AppNavKey
import com.rxth.multimodule.navigation.AppNavigation
import com.rxth.multimodule.navigation.Navigator
import com.rxth.multimodule.navigation.rememberNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMaterialTheme {
                // Track the currently selected top-level destination
                var selectedTab by retain { mutableStateOf<NavKey>(AppNavKey.Dashboard) }

                val dashboardNavigator = rememberNavigator(AppNavKey.Dashboard)
                val transactionsNavigator = rememberNavigator(AppNavKey.Transactions)
                val reportsNavigator = rememberNavigator(AppNavKey.Reports)
                val profileNavigator = rememberNavigator(AppNavKey.Profile)


                val appNavigator = when (selectedTab) {
                    AppNavKey.Dashboard -> dashboardNavigator
                    AppNavKey.Transactions -> transactionsNavigator
                    AppNavKey.Reports -> reportsNavigator
                    AppNavKey.Profile -> profileNavigator
                    else -> dashboardNavigator
                }

                val entryProvider = remember(appNavigator) {
                    AppNavigation(
                        navigator = appNavigator,
                        onSwitchTabs = { tabKey, destKey ->
                            selectedTab = tabKey
                            if (destKey != null) {
                                val targetNavigator = when (tabKey) {
                                    AppNavKey.Dashboard -> dashboardNavigator
                                    AppNavKey.Transactions -> transactionsNavigator
                                    AppNavKey.Reports -> reportsNavigator
                                    AppNavKey.Profile -> profileNavigator
                                    else -> null
                                }
                                targetNavigator?.navigate(destKey)
                            }
                        }
                    ).get()
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        key(selectedTab) {
                            RootContent(appNavigator, entryProvider)
                        }
                    }
                }

            }
        }
    }

    @Composable
    private fun RowScope.RootContent(
        appNavigator: Navigator,
        entryProvider: (NavKey) -> NavEntry<NavKey>
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clipToBounds() // Prevents animations from overlapping
        ) {
            NavDisplay(
                backStack = appNavigator.backStack,
                onBack = { appNavigator.pop() },
                entryProvider = entryProvider,
                transitionSpec = { appDefaultTransition },
                popTransitionSpec = { appDefaultTransition },
                predictivePopTransitionSpec = { appDefaultTransition }
            )
        }
    }

    private val appDefaultTransition =
        (slideInHorizontally { it } + fadeIn()) togetherWith (slideOutHorizontally { -it } + fadeOut())
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val key: NavKey
)
