package com.rxth.multimodule.feature.home.presentation.state

import com.rxth.multimodule.feature.home.domain.model.UpComingMovies

data class HomeScreenState(
    val isLoading: Boolean = false,
    val upComingMovies: UpComingMovies ?= null
)