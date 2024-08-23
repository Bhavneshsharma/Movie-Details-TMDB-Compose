package com.bhavneshsharma.moviedetailstmdbcompose.data.repository

import android.util.Log
import com.bhavneshsharma.moviedetailstmdbcompose.R
import com.bhavneshsharma.moviedetailstmdbcompose.data.model.MovieResponse
import com.bhavneshsharma.moviedetailstmdbcompose.network.NetworkResult
import com.bhavneshsharma.moviedetailstmdbcompose.network.service.MovieListService
import com.bhavneshsharma.moviedetailstmdbcompose.utils.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieListRepo(
    private val movieListService: MovieListService,
    private val resourcesProvider: ResourcesProvider
) {
    fun getTrendingMovies(): Flow<NetworkResult<MovieResponse>> =
        flow<NetworkResult<MovieResponse>> {
            emit(NetworkResult.Loading())
            val response = movieListService.getTrendingMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
                    ?: emit(NetworkResult.Error(resourcesProvider.getString(R.string.network_empty_response)))
            } else {
                Log.d(
                    MovieResponse::class.simpleName,
                    resourcesProvider.getString(R.string.error) + response.message()
                )
                emit(NetworkResult.Error(resourcesProvider.getString(R.string.something_went_wrong)))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(NetworkResult.Error(resourcesProvider.getString(R.string.something_went_wrong)))
        }.flowOn(Dispatchers.IO)
}
