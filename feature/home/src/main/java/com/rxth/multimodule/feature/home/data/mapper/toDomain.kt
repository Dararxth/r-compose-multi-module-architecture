package com.rxth.multimodule.feature.home.data.mapper

import com.rxth.multimodule.feature.home.data.dto.DatesDto
import com.rxth.multimodule.feature.home.data.dto.MovieDto
import com.rxth.multimodule.feature.home.data.dto.UpComingMoviesDto
import com.rxth.multimodule.feature.home.domain.model.Dates
import com.rxth.multimodule.feature.home.domain.model.Movie
import com.rxth.multimodule.feature.home.domain.model.UpComingMovies

fun UpComingMoviesDto.toDomain(): UpComingMovies = UpComingMovies(
    dates = this.dates?.toDomain() ?: Dates("", ""),
    page = this.page ?: 0,
    results = this.results?.map { it.toDomain() } ?: emptyList(),
    totalPages = this.totalPages ?: 0,
    totalResults = this.totalResults ?: 0
)

fun DatesDto.toDomain(): Dates = Dates(
    maximum = this.maximum.orEmpty(),
    minimum = this.minimum.orEmpty()
)

fun MovieDto.toDomain(): Movie = Movie(
    adult = this.adult ?: false,
    backdropPath = this.backdropPath.orEmpty(),
    genreIds = this.genreIds ?: emptyList(),
    id = this.id ?: 0,
    originalLanguage = this.originalLanguage.orEmpty(),
    originalTitle = this.originalTitle.orEmpty(),
    overview = this.overview.orEmpty(),
    popularity = this.popularity ?: 0.0,
    posterPath = this.posterPath.orEmpty(),
    releaseDate = this.releaseDate.orEmpty(),
    title = this.title.orEmpty(),
    video = this.video ?: false,
    voteAverage = this.voteAverage ?: 0.0,
    voteCount = this.voteCount ?: 0
)