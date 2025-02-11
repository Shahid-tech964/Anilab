package com.example.anilab.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.anilab.Repository.DefualtRepository
import com.example.anilab.StateHandling.DefualtState
import com.example.anilab.StateHandling.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

// for default search page
@HiltViewModel
class DefaultSearchViewmodel @Inject constructor(private val myrespository: DefualtRepository):
    ViewModel() {
    private  val _result= MutableLiveData<DefualtState>()
    val result: LiveData<DefualtState> =_result

    fun  getState(){
//        _result.value= HomeState.loading
        viewModelScope.launch {

            myrespository.store_response1().catch {e->
                when(e){
                    is IOException ->_result.value= DefualtState.failure("Please check your internet connection ")
                    is HttpException -> HomeState.failure("Server side exception.Please try again later ")
                    else-> HomeState.failure("Something went wrong!")
                }

            }
                .collect{
                    Log.d("Response", "${it.data.size}")
                    _result.value= DefualtState.success(it)
//
                }



        }
    }
}