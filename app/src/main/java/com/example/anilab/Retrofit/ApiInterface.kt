package com.example.anilab.Retrofit

import com.example.anilab.model_class.AnimeInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//for anime detail
interface apiInterface1 {
//    end point
    @GET("top/anime")
    suspend fun getResponse(@Query("page")page:Int):Response<AnimeInfo>
}



//for Search anime
interface apiinterface2{

    @GET("anime")

    suspend fun getResponse2(@Query("q")name:String):Response<AnimeInfo>

}