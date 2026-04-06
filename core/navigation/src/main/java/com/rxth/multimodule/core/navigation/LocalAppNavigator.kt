package com.rxth.multimodule.core.navigation
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppNavigator = staticCompositionLocalOf<Navigator?> {
    error("CompositionLocal Navigator not present")
}