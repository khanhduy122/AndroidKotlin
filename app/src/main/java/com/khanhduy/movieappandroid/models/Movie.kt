package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val _id: String,
    val name: String,
    val origin_name: String,
    val poster_url: String,
    val slug: String,
    val thumb_url: String,
    val year: Int
) : Parcelable