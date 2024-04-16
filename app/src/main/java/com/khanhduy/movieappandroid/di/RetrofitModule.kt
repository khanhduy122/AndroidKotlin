package com.khanhduy.movieappandroid.di

import com.khanhduy.movieappandroid.data.api.ApiConstant
import com.khanhduy.movieappandroid.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun providerRetrofit () : ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java);
    }
}