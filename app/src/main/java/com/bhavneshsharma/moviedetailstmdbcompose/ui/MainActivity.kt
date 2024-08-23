package com.bhavneshsharma.moviedetailstmdbcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bhavneshsharma.moviedetailstmdbcompose.ui.navigation.MovieDetailNavHost
import com.bhavneshsharma.moviedetailstmdbcompose.ui.theme.MovieDetailsTMDBComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    MovieDetailsTMDBComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navHostController = rememberNavController()
            MovieDetailNavHost(navHostController = navHostController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieDetailsTMDBComposeTheme {
        App()
    }
}