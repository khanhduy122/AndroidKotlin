package com.khanhduy.movieappandroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val _id: String,
    val actor: List<String>,
    val category: List<Category>,
    val chieurap: Boolean,
    val content: String,
    val country: List<Country>,
    val director: List<String>,
    val episode_current: String,
    val episode_total: String,
    val is_copyright: Boolean,
    val lang: String,
    val name: String,
    val notify: String,
    val origin_name: String,
    val poster_url: String,
    val quality: String,
    val showtimes: String,
    val slug: String,
    val status: String,
    val sub_docquyen: Boolean,
    val thumb_url: String,
    val time: String,
    val trailer_url: String,
    val type: String,
    val view: Int,
    val year: Int
) : Parcelable{
    fun getCategories() : String{
        var categories : String = ""
        category.forEach {
            categories += "${it.name}, "
        }
        return categories.substring(0, categories.length-2)
    }
    fun getDirector() : String{
        var directors : String = ""
        director.forEach {
            directors += "${it}, "
        }
        return directors.substring(0, directors.length-2)
    }

    fun getCountry() : String{
        var countrys : String = ""
        country.forEach {
            countrys += "${it.name}, "
        }
        return countrys.substring(0, countrys.length-2)
    }

    fun getActor() : String{
        var actors : String = ""
        actor.forEach {
            actors += "${it}, "
        }
        return actors.substring(0, actors.length-2)
    }
}