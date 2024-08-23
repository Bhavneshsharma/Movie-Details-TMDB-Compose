package com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavneshsharma.moviedetailstmdbcompose.data.model.Movie
import com.bhavneshsharma.moviedetailstmdbcompose.data.repository.MovieListRepo
import com.bhavneshsharma.moviedetailstmdbcompose.network.NetworkResult
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.actionData.MovieListActionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepo: MovieListRepo
) : ViewModel() {

    init {
        getMovieListData()
    }

    private val originalMovieList = mutableListOf<Movie>()

    private val _searchItem = MutableStateFlow<String?>(null)
    val searchItem: StateFlow<String?> = _searchItem

    private val _movieListActionData = MutableStateFlow(MovieListActionData())
    val movieListActionData: StateFlow<MovieListActionData> = _movieListActionData

    fun setSearchItem(name: String?) {
        _searchItem.value = name
        _movieListActionData.value = MovieListActionData(
            isLoading = false,
            data = originalMovieList
                .let { list ->
                    if (name.isNullOrBlank()) list
                    else list.filter { it.title.contains(name, ignoreCase = true) }
                },
            error = null
        )
    }

    fun getMovieListData() {
        viewModelScope.launch {
            movieListRepo.getTrendingMovies().collect { result ->
                when (result) {
                    is NetworkResult.Error -> handelMovieListError(result.message)
                    is NetworkResult.Loading -> handelMovieListLoading()
                    is NetworkResult.Success -> handelMovieListSuccess(result.data?.results)
                }
            }
        }
    }

    private fun handelMovieListSuccess(data: List<Movie>?) {
        originalMovieList.clear()
        if (data != null) {
            originalMovieList.addAll(data)
        }
        setSearchItem(null)
    }

    private fun handelMovieListLoading() {
        _movieListActionData.value = MovieListActionData(isLoading = true, data = emptyList())
    }

    private fun handelMovieListError(message: String?) {
        _movieListActionData.value =
            MovieListActionData(isLoading = false, data = emptyList(), error = message)
    }
}