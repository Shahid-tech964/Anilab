package com.example.anilab.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [fav_Table::class], version = 1)
abstract class RoomDB:RoomDatabase() {
    abstract   fun getDao():Dao_interface
}