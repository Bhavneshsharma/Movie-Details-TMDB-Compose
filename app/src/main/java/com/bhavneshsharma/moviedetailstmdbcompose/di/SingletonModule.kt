package com.bhavneshsharma.moviedetailstmdbcompose.di

import android.content.Context
import com.bhavneshsharma.moviedetailstmdbcompose.network.interceptor.AuthInterceptor
import com.bhavneshsharma.moviedetailstmdbcompose.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideResourceProvide(@ApplicationContext context: Context): ResourcesProvider {
        return ResourcesProvider(context)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        resourcesProvider: ResourcesProvider
    ): AuthInterceptor {
        return AuthInterceptor(resourcesProvider)
    }
}