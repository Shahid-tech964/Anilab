package com.example.anilab.Main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent

import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider

import com.example.anilab.Viewmodel.DefaultSearchViewmodel
import com.example.anilab.Viewmodel.HomeViewmodel
import com.example.anilab.Viewmodel.fav_viewmodel

import com.example.anilab.Viewmodel.filterViewmodel
import com.example.anilab.ui.theme.AnilabTheme

//import com.example.anilab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        val Homeinstance : HomeViewmodel by viewModels()
        val Defaultinstance:DefaultSearchViewmodel by viewModels()
        val Searchinstance : filterViewmodel by viewModels()
        val fav_instance= ViewModelProvider(this).get(fav_viewmodel::class.java)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor= Color.Transparent.toArgb()
        window.navigationBarColor= Color.Black.toArgb()

        setContent {
    AnilabTheme {

        Bottom_Nav(Homeinstance, Defaultinstance, Searchinstance,fav_instance)

    }


        }

    }
}
