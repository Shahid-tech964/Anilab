package com.example.anilab.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.anilab.Repository.HomeRepository


import com.example.anilab.StateHandling.HomeState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

//for home page
@HiltViewModel
class HomeViewmodel @Inject constructor(private val myrespository: HomeRepository):ViewModel() {
  private val _result= MutableLiveData<HomeState>()
    val result:LiveData<HomeState> =_result

    fun  getState(){

        viewModelScope.launch {

                myrespository.store_response1().catch {e->
                 _result.value=
                     when(e){
                         is IOException->HomeState.failure("Please check your internet connection")
                         is HttpException->HomeState.failure("Server side exception.Please try again later ")
                         else->HomeState.failure("Something went wrong!")
                     }

                }
                    .collect{
                        Log.d("Response", "${it.data.size}")
                        _result.value= HomeState.success(it)

                    }



        }
    }
}















