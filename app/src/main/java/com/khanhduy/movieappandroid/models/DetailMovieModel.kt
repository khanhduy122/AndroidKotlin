package com.khanhduy.movieappandroid.models

data class DetailMovieModel(
    val episodes: List<Episode>,
    val movie: Movie,
    val msg: String,
    val status: Boolean
)