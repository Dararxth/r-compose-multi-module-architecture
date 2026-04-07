package com.rxth.multimodule.feature.home.presentation.state
sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
}