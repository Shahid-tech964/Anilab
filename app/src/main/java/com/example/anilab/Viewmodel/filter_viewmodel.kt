package com.example.anilab.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.anilab.Retrofit.apiinterface2
import com.example.anilab.StateHandling.SearchStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

//for filtering anime based on query
@HiltViewModel
class filterViewmodel @Inject constructor(val Apiinterface2: apiinterface2): ViewModel()
{
    private val _result= MutableLiveData<SearchStates>()
    val result: LiveData<SearchStates> =_result




    fun getState(title:String){
        viewModelScope.launch {
            _result.value= SearchStates.loading
            try {

                val response =Apiinterface2.getResponse2(title)

                if(response.isSuccessful){
                    if (response.body()?.data?.isEmpty() == true) {
                        _result.value= SearchStates.Notfound("Sorry! Data not found")
                    }

                    else{
                        Log.d("success", "data found")
                        response.body()?.let {
                            _result.value = SearchStates.sucsess(it)
                        }
                    }
                }
            }

            catch (e:Exception){
                when(e){
                    is IOException ->{
                        _result.value= SearchStates.Failure("Please check your internet connection ")
                    }
                    is HttpException ->{
                        _result.value= SearchStates.Failure("Server side exception.Please try again later")
                    }
                    else->{
                        _result.value= SearchStates.Failure("Something went wrong")
                    }
                }
            }

        }
    }
}