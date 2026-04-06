package com.rxth.multimodule.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey {
    @Serializable
    data object Home : AppNavKey

    @Serializable
    data class Detail(val id: Int) : AppNavKey

    @Serializable
    data class AnotherDetail(val id: Int) : AppNavKey

}
