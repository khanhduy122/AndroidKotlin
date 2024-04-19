package com.khanhduy.movieappandroid.screen.playVideo

import android.util.Log
import androidx.compose.runtime.Composable
import com.khanhduy.movieappandroid.models.DetailMovieModel

@Composable
fun PlayVideoScreen(detailMovieModel: DetailMovieModel, episode : Int){
    Log.e("bbb", "PlayVideoScreen: ${detailMovieModel?.movie?.name}", )
}