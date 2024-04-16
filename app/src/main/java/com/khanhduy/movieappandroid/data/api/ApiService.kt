package com.khanhduy.movieappandroid.data.api

import com.khanhduy.movieappandroid.models.NewMovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstant.urlNewMovie)
    suspend fun getNewMovie(
        @Query("page") page : Int
    ) : Response<NewMovieModel>
}