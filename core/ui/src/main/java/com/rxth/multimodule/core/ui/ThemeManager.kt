package com.rxth.multimodule.core.ui

import kotlinx.coroutines.flow.MutableStateFlow

object ThemeManager {
    val theme = MutableStateFlow(ThemeMode.SYSTEM)

    fun setTheme(mode: ThemeMode) {
        theme.value = mode
    }
}