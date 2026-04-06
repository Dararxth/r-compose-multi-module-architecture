package com.rxth.multimodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class Navigator(
    val backStack: MutableList<NavKey>
) {
    fun navigate(dest: NavKey) {
        backStack.add(dest)
    }

    fun goBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }
}

@Composable
fun rememberNavigator(startDestination: NavKey = AppNavKey.Home): Navigator {
    val backStack = rememberNavBackStack(startDestination)
    return remember(backStack) { Navigator(backStack) }
}
