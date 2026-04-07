package com.rxth.multimodule.feature.home.domain.usecase

import com.rxth.multimodule.core.network.Resource
import com.rxth.multimodule.feature.home.data.mapper.toDomain
import com.rxth.multimodule.feature.home.domain.model.UpComingMovies
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetUpComingMovieUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(page: Int): Flow<Resource<UpComingMovies>> = flow {
        emit(Resource.Loading)
        val response = repository.getUpComingMovies(page)
        emit(Resource.Success(data = response.toDomain()))
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(message = e.message ?: "Unknown Error"))
    }
}

