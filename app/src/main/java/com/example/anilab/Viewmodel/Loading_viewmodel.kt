package com.example.anilab.Viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anilab.Retrofit.apiInterface1
import com.example.anilab.StateHandling.loadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//for loading state
@HiltViewModel
class LoadingViewmodel @Inject constructor(val Apiinterface1: apiInterface1): ViewModel() {
    private val _result = MutableLiveData<loadState>()
    val result: LiveData<loadState> = _result


    @SuppressLint("SuspiciousIndentation")
    fun getState() {
        viewModelScope.launch {
            _result.value = loadState.loading
            try {
                val  data =Apiinterface1.getResponse(2)
                if(data.isSuccessful){
                    _result.value= loadState.dataloaded
                }

            }
            catch (e:Exception){

            }
        }
    }
}