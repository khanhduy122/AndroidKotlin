package com.khanhduy.movieappandroid.models

data class Params(
    val filterCategory: List<String>,
    val filterCountry: List<String>,
    val filterType: String,
    val filterYear: String,
    val pagination: Pagination,
    val sortField: String,
    val sortType: String,
    val type_slug: String
)