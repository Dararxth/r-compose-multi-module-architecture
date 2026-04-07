package com.rxth.multimodule.feature.home.presentation.state
sealed interface HomeEvent {
    data class GetUpComingMovies(val page: Int) : HomeEvent
}