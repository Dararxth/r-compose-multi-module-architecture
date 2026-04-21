package com.rxth.multimodule.navigation

import androidx.navigation3.runtime.NavKey
import com.rxth.multimodule.feature.home.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey {
    @Serializable data object Dashboard : AppNavKey
    @Serializable data object Transactions : AppNavKey
    @Serializable data object Reports : AppNavKey
    @Serializable data object Profile : AppNavKey
    @Serializable data class Detail(val movie: Movie, val listId: String) : AppNavKey
    @Serializable data class AnotherDetail(val id: Int) : AppNavKey
}
