package com.rxth.multimodule.feature.home.presentation.state

import com.rxth.multimodule.feature.home.domain.model.NowPlayingMovies
import com.rxth.multimodule.feature.home.domain.model.PopularMovies
import com.rxth.multimodule.feature.home.domain.model.UpComingMovies

data class HomeScreenState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: NowPlayingMovies ?= null,
    val popularMovies: PopularMovies?= null,
    val upComingMovies: UpComingMovies ?= null
)