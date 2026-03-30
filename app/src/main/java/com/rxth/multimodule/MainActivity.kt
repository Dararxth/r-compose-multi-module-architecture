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
import com.rxth.multimodule.core.ui.ThemeManager
import com.rxth.multimodule.core.ui.ThemeMode
import com.rxth.multimodule.core.ui.theme.AppMaterialTheme

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

                }
            }
        }
    }
}