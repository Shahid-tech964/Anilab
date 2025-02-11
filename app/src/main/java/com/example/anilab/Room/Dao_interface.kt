package com.example.anilab.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Dao_interface {
    @Query("select * from fav_Table")
      fun getItems():LiveData<List<fav_Table>>

      @Insert(onConflict = OnConflictStrategy.IGNORE)
      suspend fun  add_fav(table: fav_Table)

      @Query("delete from fav_Table where id=:id")
      suspend fun delete_row(id:Int)
}