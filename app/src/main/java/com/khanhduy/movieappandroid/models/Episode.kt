package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val server_data: List<ServerData>,
    val server_name: String
) : Parcelable