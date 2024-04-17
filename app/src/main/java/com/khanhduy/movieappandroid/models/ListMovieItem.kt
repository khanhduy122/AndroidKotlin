package com.khanhduy.movieappandroid.models

data class ListMovieItem(
    val _id: String,
    val category: List<Category>,
    val chieurap: Boolean,
    val country: List<Country>,
    val episode_current: String,
    val lang: String,
    val name: String,
    val origin_name: String,
    val poster_url: String,
    val quality: String,
    val slug: String,
    val sub_docquyen: Boolean,
    val thumb_url: String,
    val time: String,
    val type: String,
    val year: Int
){
    fun getCategories() : String{
         var categories : String = ""
        category.forEach {
            categories += "${it.name}, "
        }
        return categories.substring(0, categories.length-2)
    }
}