package com.example.anilab.Retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//anime info
//https://api.jikan.moe/v4/top/anime?page=2

//search for anime by its name
//https://api.jikan.moe/v4/anime?p=title


//Module for anime info or dependencies
@Module
@InstallIn(SingletonComponent::class)

object RetrofitModule1{

   @Provides
   @Singleton
  fun  retrofit():Retrofit{
       return  Retrofit.Builder().baseUrl("https://api.jikan.moe/v4/").
       addConverterFactory(GsonConverterFactory.create()).build()
   }

    @Provides
    fun api_service(retrofit: Retrofit): apiInterface1 {
       return retrofit.create(apiInterface1::class.java)
    }

}


//Module for anime Search or dependencies
@Module
@InstallIn(SingletonComponent::class)
object retrofitModule2 {

    @Provides
    @Singleton
    fun api_service():apiinterface2{
        return Retrofit.Builder().baseUrl("https://api.jikan.moe/v4/").addConverterFactory(GsonConverterFactory.create()).build()
            .create(apiinterface2::class.java)
    }

}




