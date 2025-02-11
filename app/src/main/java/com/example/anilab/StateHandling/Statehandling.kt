package com.example.anilab.StateHandling

import com.example.anilab.model_class.AnimeInfo

sealed class HomeState {
//
    data class  success(val s_data:AnimeInfo): HomeState()
    data class  failure(val error:String): HomeState()
}

sealed class DefualtState {

    data class  success(val s_data:AnimeInfo): DefualtState()
    data class  failure(val error:String): DefualtState()
}

sealed class SearchStates{
    data object  loading:SearchStates()
    data class Notfound(val mess:String):SearchStates()
    data class sucsess(val data:AnimeInfo):SearchStates()
    data class Failure(val error:String):SearchStates()
}


sealed class loadState {
  data  object  loading:loadState()
    data  object dataloaded :loadState()
}