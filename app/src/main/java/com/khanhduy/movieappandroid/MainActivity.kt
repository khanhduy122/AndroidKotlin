package com.khanhduy.movieappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khanhduy.movieappandroid.screen.main.MainScreen
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
fun MainApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = GraphRoot.Splash.route ){
        composable(route = GraphRoot.Splash.route){
            SplashScreen(navController)
        }
        composable(route = GraphRoot.Main.route){
            MainScreen()
        }
    }
}

sealed class GraphRoot(val route: String){
    object Splash : GraphRoot(route = "SplashScreen")
    object Main : GraphRoot(route = "MainScreen")
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppAndroidTheme {
        MainApp()
    }
}