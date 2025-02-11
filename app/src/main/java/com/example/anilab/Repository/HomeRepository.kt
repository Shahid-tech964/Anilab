package com.example.anilab.Repository

import android.annotation.SuppressLint
import com.example.anilab.Retrofit.apiInterface1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


//for Home page
class HomeRepository @Inject constructor(val api_interface1: apiInterface1) {



    @SuppressLint("SuspiciousIndentation")
    fun store_response1()= flow {

        try {
            val data = api_interface1.getResponse(2)

                data.body()?.let {
                    emit(it)
                }

        }
       catch (e:Exception){
           throw e
       }

    }.flowOn(Dispatchers.IO)


}


