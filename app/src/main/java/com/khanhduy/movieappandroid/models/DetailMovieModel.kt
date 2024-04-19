package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovieModel(
    val episodes: List<Episode>,
    val movie: Movie,
    val msg: String,
    val status: Boolean
)  : Parcelable