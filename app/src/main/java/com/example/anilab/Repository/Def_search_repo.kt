package com.example.anilab.Repository

import com.example.anilab.Retrofit.apiInterface1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// default search  repository
class DefualtRepository @Inject constructor(val api_interface1: apiInterface1) {


    fun store_response1()= flow {

        try {
            val data=api_interface1.getResponse(3)

            data.body()?.let {
                emit(it)
            }
        }
        catch (e:Exception){
            throw e
        }



    }.flowOn(Dispatchers.IO)


}

