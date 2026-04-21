package com.rxth.multimodule.feature.home.domain.usecase

import com.rxth.multimodule.core.network.Resource
import com.rxth.multimodule.feature.home.data.mapper.toNowPlayingMovies
import com.rxth.multimodule.feature.home.domain.model.NowPlayingMovies
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetNowPlayingMovieUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(page: Int): Flow<Resource<NowPlayingMovies>> = flow {
        emit(Resource.Loading)
        val response = repository.getNowPlayingMovies(page)
        emit(Resource.Success(data = response.toNowPlayingMovies()))
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(message = e.message ?: "Unknown Error"))
    }
}

