package com.example.anilab

import android.app.Application
import androidx.room.Room
import com.example.anilab.Room.RoomDB
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltAndroidApp
class baseclass:Application(){

    companion object{
        lateinit var dbinstance:RoomDB
    }
    override fun onCreate() {
        super.onCreate()
      dbinstance=  Room.databaseBuilder(applicationContext,RoomDB::class.java,"RoomDB").build()
    }
}