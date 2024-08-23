package com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.actionData

import com.bhavneshsharma.moviedetailstmdbcompose.data.model.Movie

data class MovieListActionData(
    val isLoading: Boolean = false,
    val data: List<Movie> = emptyList(),
    val error: String? = null
)