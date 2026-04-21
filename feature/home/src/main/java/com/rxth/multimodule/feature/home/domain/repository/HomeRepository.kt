package com.rxth.multimodule.feature.home.domain.repository

import com.rxth.multimodule.feature.home.data.dto.MoviesResponseDto


interface HomeRepository {

    suspend fun getPopularMovies(page: Int): MoviesResponseDto
    suspend fun getNowPlayingMovies(page: Int): MoviesResponseDto
    suspend fun getUpComingMovies(page: Int): MoviesResponseDto

}