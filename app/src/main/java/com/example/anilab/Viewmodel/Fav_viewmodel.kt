package com.example.anilab.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anilab.Room.fav_Table
import com.example.anilab.baseclass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//for favourite page
class fav_viewmodel: ViewModel(){
    val getDao= baseclass.dbinstance.getDao()

    //    retrieving all the row  present in table
    val rows: LiveData<List<fav_Table>> =getDao.getItems()

    //    add item in table
    fun addItem(larg_img:String,sml_img:String,name:String,genre:String,synopsis:String,rating:Float,release :Int,status:String,episode:Int){
        viewModelScope.launch(Dispatchers.IO) {
            getDao.add_fav(fav_Table(0,larg_img,sml_img,name,genre,synopsis,release,rating,status,episode))
        }
    }

    //    delete row from table
    fun deleteItem(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            getDao.delete_row(id)
        }
    }


}
