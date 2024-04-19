package com.khanhduy.movieappandroid.repository

import android.content.Context
import android.util.Log
import com.khanhduy.movieappandroid.data.api.ApiService
import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.models.ListMovieModel
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.utils.NetworkInfo
import kotlinx.coroutines.currentCoroutineContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.reflect.typeOf

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNewMovie(page: Int): AppResponse<NewMovieModel> {
//        if(!NetworkInfo.isNetworkAvailable(context = coroutineContext())){
//            return AppResponse.Error("No Internet Connection");
//        }
        try {
            val response = apiService.getNewMovie(page)
            if (response.body() != null) {
                return AppResponse.Success<NewMovieModel>(response.body())
            }
        } catch (error: Exception) {
            return handleError(error);
        }
        return AppResponse.Error("An error occurred, please try again");
    }


    suspend fun getSeriwsMovie(page: Int): AppResponse<ListMovieModel> {
//        if(!NetworkInfo.isNetworkAvailable(context = coroutineContext())){
//            return AppResponse.Error("No Internet Connection");
//        }
        try {
            val response = apiService.getSeriesMovie(page)
            if (response.body() != null) {
                return AppResponse.Success<ListMovieModel>(response.body())
            }
        } catch (error: Exception) {
            return handleError(error);
        }
        return AppResponse.Error("An error occurred, please try again");
    }


    suspend fun getSingleMovie(page: Int): AppResponse<ListMovieModel> {
//        if(!NetworkInfo.isNetworkAvailable(context = coroutineContext())){
//            return AppResponse.Error("No Internet Connection");
//        }
        try {
            val response = apiService.getSingleMovie(page)
            if (response.body() != null) {
                return AppResponse.Success<ListMovieModel>(response.body())
            }
        } catch (error: Exception) {
            return handleError(error);
        }
        return AppResponse.Error("An error occurred, please try again");
    }


    suspend fun getCartoonMovie(page: Int): AppResponse<ListMovieModel> {
//        if(!NetworkInfo.isNetworkAvailable(context = coroutineContext())){
//            return AppResponse.Error("No Internet Connection");
//        }
        try {
            val response = apiService.getCartoonMovie(page)
            if (response.body() != null) {
                return AppResponse.Success<ListMovieModel>(response.body())
            }
        } catch (error: Exception) {
            return handleError(error);
        }
        return AppResponse.Error("An error occurred, please try again");
    }


    suspend fun getDetailMovie(slug: String): AppResponse<DetailMovieModel> {
//        if(!NetworkInfo.isNetworkAvailable(context = coroutineContext())){
//            return AppResponse.Error("No Internet Connection");
//        }
        try {
            val response = apiService.getDetaileMovie(slug)
            if (response.body() != null) {
                return AppResponse.Success<DetailMovieModel>(response.body())
            }
        } catch (error: Exception) {
            return handleError(error);
        }
        return AppResponse.Error("An error occurred, please try again");
    }

    private fun <T> handleError(error: Exception): AppResponse<T> {
        when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 400..499 -> {
                        return AppResponse.Error("Client Error");
                    }

                    in 500..599 -> {
                        return AppResponse.Error("Server Error");
                    }

                    else -> {
                        return AppResponse.Error("Unknown");
                    }
                }
            }

            is IOException -> {
                return AppResponse.Error("NetWorkException");
            }
        }
        return AppResponse.Error("An error occurred, please try again");
    }
}