package com.rxth.multimodule.feature.home.domain.model


data class PopularMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class NowPlayingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class UpComingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
