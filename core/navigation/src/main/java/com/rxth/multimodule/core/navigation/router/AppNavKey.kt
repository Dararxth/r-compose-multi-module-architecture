package com.rxth.multimodule.core.navigation.router

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey {
    @Serializable data object Dashboard : AppNavKey
    @Serializable data object Transactions : AppNavKey
    @Serializable data object Reports : AppNavKey
    @Serializable data object Profile : AppNavKey
    @Serializable data class Detail(val id: Int) : AppNavKey
    @Serializable data class AnotherDetail(val id: Int) : AppNavKey
}
