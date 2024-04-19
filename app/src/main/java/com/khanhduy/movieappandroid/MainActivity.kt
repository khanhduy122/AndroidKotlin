package com.khanhduy.movieappandroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.screen.detail.DetailMovieScreen
import com.khanhduy.movieappandroid.screen.main.MainScreen
import com.khanhduy.movieappandroid.screen.playVideo.PlayVideoScreen
import com.khanhduy.movieappandroid.screen.splash.SplashScreen
import com.khanhduy.movieappandroid.ui.theme.MovieAppAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppAndroidTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = GraphRoot.Splash.route) {
        composable(route = GraphRoot.Splash.route) {
            SplashScreen(navController)
        }

        composable(route = GraphRoot.Main.route) {
            MainScreen(navController)
        }

        composable(
            route = "${GraphRoot.Detail.route}/{slug}",
            arguments = listOf(navArgument("slug") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug")!!
            DetailMovieScreen(slug = slug, navController)
        }

        composable(route = GraphRoot.PlayVideo.route) { backStackEntry ->
            val detailMovieModel =
                navController.previousBackStackEntry?.savedStateHandle?.get<DetailMovieModel>(
                    key = "detailMovieModel"
                )
            val episode =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(key = "episode")
            PlayVideoScreen(detailMovieModel = detailMovieModel!!, episode!!)
        }
    }
}

sealed class GraphRoot(val route: String) {
    object Splash : GraphRoot(route = "SplashScreen")
    object Main : GraphRoot(route = "MainScreen")
    object Detail : GraphRoot(route = "DetailScreen")
    object PlayVideo : GraphRoot(route = "PlayVideoScreen")
}

