package com.rxth.multimodule.feature.home.di

import com.rxth.multimodule.core.network.constance.NetworkConstance
import com.rxth.multimodule.feature.home.data.remote.HomeApiInterface
import com.rxth.multimodule.feature.home.data.repository.HomeRepositoryImpl
import com.rxth.multimodule.feature.home.domain.repository.HomeRepository
import com.rxth.multimodule.feature.home.domain.usecase.GetNowPlayingMovieUseCase
import com.rxth.multimodule.feature.home.domain.usecase.GetPopularMovieUseCase
import com.rxth.multimodule.feature.home.domain.usecase.GetUpComingMovieUseCase
import com.rxth.multimodule.feature.home.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single<HomeApiInterface> {
        get<Retrofit>(named(NetworkConstance.DEFAULT_RETROFIT)).create(HomeApiInterface::class.java)
    }


    singleOf(
        ::HomeRepositoryImpl
    ) bind HomeRepository::class


    factoryOf(::GetUpComingMovieUseCase)
    factoryOf(::GetPopularMovieUseCase)
    factoryOf(::GetNowPlayingMovieUseCase)
    viewModelOf(
        ::HomeScreenViewModel

    )
}