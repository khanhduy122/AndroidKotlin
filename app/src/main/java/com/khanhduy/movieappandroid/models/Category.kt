package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: String,
    val name: String,
    val slug: String
) : Parcelable