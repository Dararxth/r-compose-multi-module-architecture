package com.rxth.multimodule.feature.home.domain.usecase

import com.rxth.multimodule.core.network.Resource
import com.rxth.multimodule.feature.home.data.mapper.toPopularMovies
import com.rxth.multimodule.feature.home.domain.model.PopularMovies
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetPopularMovieUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(page: Int): Flow<Resource<PopularMovies>> = flow {
        emit(Resource.Loading)
        val response = repository.getPopularMovies(page)
        emit(Resource.Success(data = response.toPopularMovies()))
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(message = e.message ?: "Unknown Error"))
    }
}

