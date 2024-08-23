package com.bhavneshsharma.moviedetailstmdbcompose.di

import com.bhavneshsharma.moviedetailstmdbcompose.network.interceptor.AuthInterceptor
import com.bhavneshsharma.moviedetailstmdbcompose.network.service.MovieListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMovieListService(authInterceptor: AuthInterceptor): MovieListService {
        return MovieListService.create(authInterceptor)
    }
}