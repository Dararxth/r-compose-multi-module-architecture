package com.rxth.multimodule.feature.home.data.repository

import com.rxth.multimodule.feature.home.data.dto.MoviesResponseDto
import com.rxth.multimodule.feature.home.data.remote.HomeApiInterface
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val api: HomeApiInterface
) : HomeRepository {

    override suspend fun getPopularMovies(page: Int): MoviesResponseDto {
        val result = api.getPopularMovies(page)
        return result
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesResponseDto {
        val result = api.getNowPlayingMovies(page)
        return result
    }

    override suspend fun getUpComingMovies(page: Int): MoviesResponseDto {
        val result = api.getUpComingMovies(page)
        return result
    }

}