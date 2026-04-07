package com.rxth.multimodule.feature.home.data.remote

import com.rxth.multimodule.feature.home.data.dto.UpComingMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en",
    ): UpComingMoviesDto

}