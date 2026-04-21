package com.rxth.multimodule.feature.home.data.remote

import com.rxth.multimodule.feature.home.data.dto.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en",
    ): MoviesResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int?,
        @Query("language") language: String = "en",
    ): MoviesResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int?,
        @Query("language")  language: String = "en",
    ): MoviesResponseDto

}