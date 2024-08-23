package com.bhavneshsharma.moviedetailstmdbcompose.network.service

import com.bhavneshsharma.moviedetailstmdbcompose.BuildConfig
import com.bhavneshsharma.moviedetailstmdbcompose.data.model.MovieResponse
import com.bhavneshsharma.moviedetailstmdbcompose.network.interceptor.AuthInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListService : BaseService {

    @GET("trending/movie/{timeWindow}")
    suspend fun getTrendingMovies(
        @Path("timeWindow") timeWindow: String = "week",
        @Query("language") language: String = "en-US",
    ): Response<MovieResponse>

    companion object {
        fun create(authInterceptor: AuthInterceptor): MovieListService = BaseService.createService(
            clazz = MovieListService::class.java,
            authInterceptor = authInterceptor,
            baseUrl = BuildConfig.BASE_URL
        )
    }
}