package com.rxth.multimodule.feature.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rxth.multimodule.core.common.base.BaseViewModel
import com.rxth.multimodule.core.network.Resource
import com.rxth.multimodule.feature.home.domain.usecase.GetNowPlayingMovieUseCase
import com.rxth.multimodule.feature.home.domain.usecase.GetPopularMovieUseCase
import com.rxth.multimodule.feature.home.domain.usecase.GetUpComingMovieUseCase
import com.rxth.multimodule.feature.home.presentation.state.HomeEffect
import com.rxth.multimodule.feature.home.presentation.state.HomeEvent
import com.rxth.multimodule.feature.home.presentation.state.HomeScreenState
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val getPopularMoviesUseCase: GetPopularMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMovieUseCase,
    private val getUpComingMovieUseCase: GetUpComingMovieUseCase,
) : BaseViewModel<HomeScreenState, HomeEvent, HomeEffect>() {

    override fun initialState() = HomeScreenState()


    init {
        onEvent(HomeEvent.Init)
    }

    fun onEvent(e: HomeEvent) {
        when (e) {
            HomeEvent.Init -> loadAllMovies()
        }
    }
    private fun loadAllMovies() {
        getUpComingMovies()
        getPopularMovies()
        getNowPlayingMovies()
    }


    fun getUpComingMovies () {
        viewModelScope.launch {
            getUpComingMovieUseCase(1).collect {
                when(it) {
                    Resource.Loading -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is Resource.Error -> {
                        sendEffect(HomeEffect.ShowError(it.message))
                    }
                    is Resource.Success -> {
                        updateState {
                            copy(upComingMovies = it.data)
                        }
                    }
                }
            }
        }
    }
    fun getPopularMovies () {
        viewModelScope.launch {
            getPopularMoviesUseCase(1).collect {
                when(it) {
                    Resource.Loading -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is Resource.Error -> {
                        sendEffect(HomeEffect.ShowError(it.message))
                    }
                    is Resource.Success -> {
                        updateState {
                            copy(popularMovies = it.data)
                        }
                    }
                }
            }
        }
    }
    fun getNowPlayingMovies () {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase(1).collect {
                when(it) {
                    Resource.Loading -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is Resource.Error -> {
                        sendEffect(HomeEffect.ShowError(it.message))
                    }
                    is Resource.Success -> {
                        updateState {
                            copy(nowPlayingMovies = it.data)
                        }
                    }
                }
            }
        }
    }
}