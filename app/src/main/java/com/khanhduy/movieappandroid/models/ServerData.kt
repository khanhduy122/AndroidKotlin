package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServerData(
    val filename: String,
    val link_embed: String,
    val link_m3u8: String,
    val name: String,
    val slug: String
) : Parcelable