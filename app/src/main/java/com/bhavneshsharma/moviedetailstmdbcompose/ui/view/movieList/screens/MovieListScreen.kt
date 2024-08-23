package com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhavneshsharma.moviedetailstmdbcompose.BuildConfig
import com.bhavneshsharma.moviedetailstmdbcompose.R
import com.bhavneshsharma.moviedetailstmdbcompose.data.model.Movie
import com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation.ScreenMovieDetail
import com.bhavneshsharma.moviedetailstmdbcompose.ui.view.movieList.viewModel.MovieListViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel,
    onItemClick: (ScreenMovieDetail) -> Unit
) {
    val searchItem by viewModel.searchItem.collectAsState()
    val movieListDataState by viewModel.movieListActionData.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRefreshing) {
        coroutineScope.launch {
            viewModel.getMovieListData()
        }
        isRefreshing = false
    }

    if (movieListDataState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.DarkGray, modifier = Modifier.size(70.dp))
        }
    } else {
        movieListDataState.error?.let {
            ErrorScreen(
                icon = Icons.Default.Warning,
                message = it,
                isErrorPage = true,
                onRetryClick = { viewModel.getMovieListData() })
        }
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 60.dp)
                ) {
                    SearchTab(searchItem, onSearchValueChange = { viewModel.setSearchItem(it) })
                }
            }
        ) { innerPadding ->
            if (movieListDataState.data.isEmpty()) {
                ErrorScreen(
                    icon = Icons.Default.Search,
                    message = stringResource(R.string.no_data_found)
                )
            } else {
                PullToRefreshBox(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = { isRefreshing = true },
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            state = pullToRefreshState,
                            isRefreshing = isRefreshing
                        )
                    }
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 20.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(movieListDataState.data.size) { index ->
                            val currentItem = movieListDataState.data[index]
                            val screenMovieDetail = ScreenMovieDetail(
                                movieTitle = currentItem.title,
                                movieDescription = currentItem.overview,
                                moviePoster = BuildConfig.IMAGE_BASE_URL + currentItem.posterPath
                            )
                            ListItem(
                                modifier = Modifier.clickable { onItemClick(screenMovieDetail) },
                                headlineContent = { MovieListItem(currentItem) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    icon: ImageVector,
    message: String,
    isErrorPage: Boolean = false,
    onRetryClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, modifier = Modifier.size(60.dp), contentDescription = null)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            if (isErrorPage) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onRetryClick() }) {
                    Text(text = stringResource(R.string.please_retry))
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListItem(currentItem: Movie) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(height = 150.dp, width = 150.dp)
            ) {
                GlideImage(
                    model = BuildConfig.IMAGE_BASE_URL + currentItem.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = currentItem.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 12.dp, end = 12.dp)
            )
        }
    }
}

@Composable
fun SearchTab(searchItem: String?, onSearchValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchItem ?: "",
        onValueChange = { onSearchValueChange(it) },
        maxLines = 1,
        singleLine = true,
        placeholder = { Text(stringResource(R.string.search_movies)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp),
        keyboardActions = KeyboardActions(onSearch = {}),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
    )
}