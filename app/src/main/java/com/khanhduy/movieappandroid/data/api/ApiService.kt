package com.khanhduy.movieappandroid.data.api

import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.models.ListMovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstant.urlNewMovie)
    suspend fun getNewMovie(
        @Query("page") page : Int
    ) : Response<NewMovieModel>

    @GET(ApiConstant.urlSeriesMovie)
    suspend fun getSeriesMovie(
        @Query("page") page : Int
    ) : Response<ListMovieModel>

    @GET(ApiConstant.urlSingleMovie)
    suspend fun getSingleMovie(
        @Query("page") page : Int
    ) : Response<ListMovieModel>

    @GET(ApiConstant.urlCartoonMovie)
    suspend fun getCartoonMovie(
        @Query("page") page : Int
    ) : Response<ListMovieModel>

    @GET("${ApiConstant.urlDetaile}/{slug}")
    suspend fun getDetaileMovie(
        @Path("slug") slug : String
    ) : Response<DetailMovieModel>
}