package com.rxth.multimodule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import kotlinx.serialization.Serializable

@Serializable
sealed interface Dest : NavKey {
    @Serializable
    data object Home : Dest

    @Serializable
    data class Detail(val id: Int) : Dest

    @Serializable
    data class AnotherDetail(val id: Int) : Dest

}

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
fun rememberNavigator(startDestination: NavKey = Dest.Home): Navigator {
    val backStack = rememberNavBackStack(startDestination)
    return remember(backStack) { Navigator(backStack) }
}
