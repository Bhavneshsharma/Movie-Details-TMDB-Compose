package com.bhavneshsharma.moviedetailstmdbcompose.di

import com.bhavneshsharma.moviedetailstmdbcompose.data.repository.MovieListRepo
import com.bhavneshsharma.moviedetailstmdbcompose.network.service.MovieListService
import com.bhavneshsharma.moviedetailstmdbcompose.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {

    @Provides
    fun provideMoveListRepo(
        movieListService: MovieListService,
        resourcesProvider: ResourcesProvider
    ) = MovieListRepo(movieListService = movieListService, resourcesProvider = resourcesProvider)
}