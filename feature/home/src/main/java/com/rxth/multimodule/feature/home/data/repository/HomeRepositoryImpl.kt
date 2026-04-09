package com.rxth.multimodule.feature.home.data.repository

import com.rxth.multimodule.feature.home.data.dto.UpComingMoviesDto
import com.rxth.multimodule.feature.home.data.remote.HomeApiInterface
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val api: HomeApiInterface
) : HomeRepository {
    override suspend fun getUpComingMovies(page: Int): UpComingMoviesDto {
        val result =  api.getUpComingMovies(page)
        return result
    }
}