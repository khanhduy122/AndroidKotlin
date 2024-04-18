package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewMovieModel(
    val items: List<ItemNewMovie>,
    val pagination: Pagination,
    val status: Boolean
) : Parcelable