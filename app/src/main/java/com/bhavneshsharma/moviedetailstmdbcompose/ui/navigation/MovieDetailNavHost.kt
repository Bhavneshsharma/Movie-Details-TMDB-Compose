package com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieDetails.screens.MovieDetailScreen
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieDetails.viewModel.MovieDetailsViewModel
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.screens.MovieListScreen
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.viewModel.MovieListViewModel

@Composable
fun MovieDetailNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = ScreenMovieList) {
        composable<ScreenMovieList> {
            val viewModel: MovieListViewModel = hiltViewModel()
            MovieListScreen(
                viewModel = viewModel,
                onItemClick = {
                    navHostController.navigate(
                        ScreenMovieDetail(
                            moviePoster = it.moviePoster,
                            movieTitle = it.movieTitle,
                            movieDescription = it.movieDescription
                        )
                    )
                })
        }
        composable<ScreenMovieDetail> {
            val args = it.toRoute<ScreenMovieDetail>()
            val viewModel: MovieDetailsViewModel = hiltViewModel()
            viewModel.setArgs(args)
            MovieDetailScreen(viewModel = viewModel, onBackPressed = { navHostController.navigateUp() })
        }
    }
}