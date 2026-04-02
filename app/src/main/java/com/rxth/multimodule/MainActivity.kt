package com.rxth.multimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.rxth.multimodule.core.ui.ThemeManager
import com.rxth.multimodule.core.ui.ThemeMode
import com.rxth.multimodule.core.ui.theme.AppMaterialTheme
import com.rxth.multimodule.feature.detail.AnotherDetailScreen
import com.rxth.multimodule.feature.detail.DetailScreen
import com.rxth.multimodule.feature.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by ThemeManager.theme.collectAsState()

            val isDark = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            AnimatedContent(
                targetState = isDark,
                transitionSpec = {
                    fadeIn(tween(400)) + scaleIn(initialScale = 0.98f) togetherWith
                            fadeOut(tween(300))
                },
                label = "theme_switch"
            ) { dark ->
                AppMaterialTheme(darkTheme = dark) {
                    val navigator = rememberNavigator()

                    val entryProvider = entryProvider<NavKey> {
                        entry<Dest.Home> {
                            HomeScreen(
                                onNavigateToDetail = { id ->
                                    navigator.navigate(Dest.Detail(id))
                                }
                            )
                        }
                        entry<Dest.Detail> { key ->
                            DetailScreen(
                                id = key.id,
                                onBack = { navigator.goBack() },
                                onNext = { navigator.navigate(Dest.AnotherDetail(key.id)) }
                            )
                        }
                        entry<Dest.AnotherDetail> { key ->
                            AnotherDetailScreen(
                                id = key.id,
                                onBack = { navigator.goBack() }
                            )
                        }
                    }

                    NavDisplay(
                        backStack = navigator.backStack,
                        onBack = { navigator.goBack() },
                        entryProvider = entryProvider
                    )
                }
            }
        }
    }
}
