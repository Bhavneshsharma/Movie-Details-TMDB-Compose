package com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenMovieList

@Serializable
data class ScreenMovieDetail(
    val moviePoster: String,
    val movieTitle: String,
    val movieDescription: String
)