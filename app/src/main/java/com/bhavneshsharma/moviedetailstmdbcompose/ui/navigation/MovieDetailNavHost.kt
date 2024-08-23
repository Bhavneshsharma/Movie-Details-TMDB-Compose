package com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.screens.MovieListScreen
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.viewModel.MovieListViewModel

@Composable
fun MovieDetailNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = ScreenMovieList) {
        composable<ScreenMovieList> {
            val viewModel: MovieListViewModel = hiltViewModel()
            MovieListScreen(viewModel = viewModel, onItemClick = {})
        }
    }
}