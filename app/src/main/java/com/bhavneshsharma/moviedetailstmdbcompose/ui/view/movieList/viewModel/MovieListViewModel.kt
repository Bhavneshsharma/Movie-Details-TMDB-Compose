package com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavneshsharma.moviedetailstmdbcompose.R
import com.bhavneshsharma.moviedetailstmdbcompose.data.repository.MovieListRepo
import com.bhavneshsharma.moviedetailstmdbcompose.network.NetworkResult
import com.bhavneshsharma.moviedetailstmdbcompose.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepo: MovieListRepo,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    init {
        getMovieListData()
    }

    private fun getMovieListData() {
        viewModelScope.launch {
            movieListRepo.getTrendingMovies().collect { result ->
                when (result) {
                    is NetworkResult.Error -> Log.d(
                        this::class.java.simpleName + resourcesProvider.getString(
                            R.string.error
                        ), result.message.toString()
                    )

                    is NetworkResult.Loading -> Log.d(
                        this::class.java.simpleName + resourcesProvider.getString(
                            R.string.Loading
                        ), resourcesProvider.getString(R.string.Loading)
                    )

                    is NetworkResult.Success -> Log.d(
                        this::class.java.simpleName + resourcesProvider.getString(
                            R.string.success
                        ), result.data.toString()
                    )
                }
            }
        }
    }
}