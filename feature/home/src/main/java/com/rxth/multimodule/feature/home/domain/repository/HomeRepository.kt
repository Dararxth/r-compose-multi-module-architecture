package com.rxth.multimodule.feature.home.domain.repository

import com.rxth.multimodule.feature.home.data.dto.UpComingMoviesDto


interface HomeRepository {

    suspend fun getUpComingMovies(page: Int): UpComingMoviesDto

}