package com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation.ScreenMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(

) : ViewModel() {

    private val _detailScreenArgs = MutableStateFlow<ScreenMovieDetail?>(null)
    val detailScreenArgs: StateFlow<ScreenMovieDetail?> = _detailScreenArgs

    fun setArgs(args: ScreenMovieDetail) {
        _detailScreenArgs.value = args
    }
}