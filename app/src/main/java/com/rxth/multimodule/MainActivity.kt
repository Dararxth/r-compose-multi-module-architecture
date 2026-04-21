package com.rxth.multimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.graphicsLayer
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
                SharedTransitionLayout {
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
                            shareTransitionScope = this,
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

                    Box(modifier = Modifier.fillMaxSize().background(Color.Black)){
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
    }

    @Composable
    private fun RowScope.RootContent(
        appNavigator: Navigator,
        entryProvider: (NavKey) -> NavEntry<NavKey>
    ) {
        val appDefaultTransition = remember {
            ContentTransform(
                targetContentEnter = fadeIn(
                    animationSpec = tween(300)
                ) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it / 8 }
                ),
                initialContentExit = fadeOut(
                    animationSpec = tween(300)
                ) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it / 8 }
                )
            )
        }

        val appPopTransition = remember {
            ContentTransform(
                targetContentEnter = fadeIn(
                    animationSpec = tween(200)
                ) + slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { -it / 8 }
                ),
                initialContentExit = fadeOut(
                    animationSpec = tween(200)
                ) + slideOutHorizontally(
                    animationSpec = tween(200),
                    targetOffsetX = { it / 8 }
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clipToBounds().graphicsLayer {
                    renderEffect = null
                    clip = true
                } // Prevents animations from overlapping
        ) {
            NavDisplay(
                backStack = appNavigator.backStack,
                onBack = { appNavigator.pop() },
                entryProvider = entryProvider,
                transitionSpec = { appDefaultTransition },
                popTransitionSpec = { appPopTransition },
                predictivePopTransitionSpec = { appPopTransition }
            )
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val key: NavKey
)
