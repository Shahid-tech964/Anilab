package com.example.anilab.Room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["name"], unique = true)])
data class fav_Table(@PrimaryKey(autoGenerate = true) val id:Int,
   val large_image:String, val smal_img:String,val name:String,val genre:String,val synopsis:String,val release :Int,val rating :Float,val status:String,val episode:Int)