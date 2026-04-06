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

    fun navigate(dest: List<NavKey>) {
        dest.forEach {
            backStack.add(it)
        }
    }

    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    fun popAll() {
        while (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }
}

@Composable
fun rememberNavigator(startDestination: NavKey = AppNavKey.Dashboard): Navigator {
    val backStack = rememberNavBackStack(startDestination)
    return remember(backStack) { Navigator(backStack) }
}
