package com.example.anilab.Main

import android.annotation.SuppressLint
import android.app.Activity
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anilab.R
import com.example.anilab.Screens.Home
import com.example.anilab.Screens.Library
import com.example.anilab.Screens.Search
import com.example.anilab.Viewmodel.DefaultSearchViewmodel
import com.example.anilab.Viewmodel.HomeViewmodel
import com.example.anilab.Viewmodel.fav_viewmodel


import com.example.anilab.Viewmodel.filterViewmodel
import com.example.anilab.ui.theme.Black
import com.example.anilab.ui.theme.darkBlack


@Composable
fun Bottom_Nav(home:HomeViewmodel,default:DefaultSearchViewmodel,filter:filterViewmodel,favViewmodel: fav_viewmodel) {

    var selected by rememberSaveable  {
        mutableStateOf("home")
    }
    val snakState= remember {
        SnackbarHostState()
    }

    val navController=rememberNavController()


    Scaffold( bottomBar = {
        BottomAppBar(modifier = Modifier.navigationBarsPadding().
        fillMaxWidth().height(65.dp), containerColor = MaterialTheme.colorScheme.surface) {

//            Home icon
            IconButton(onClick = {
                if(selected!="home") {
                    selected="home"
                    navController.navigate("Home")
                }
            }, modifier = Modifier.weight(1f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        tint = if (selected == "home") Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Home", fontSize = 15.sp, color = if (selected == "home") Color.Red else Color.Gray,)
                }
            }


//            Search Icon
            IconButton(onClick = {
                if(selected!="search") {
                    selected="search"
                    navController.navigate("Search")
                }
            }, modifier = Modifier.weight(1f)) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = if (selected == "search") Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Search", fontSize = 15.sp, color =if (selected == "search") Color.Red else Color.Gray)
                }
            }

//            Faourite icon
            IconButton(onClick = {
                if(selected!="favourite") {
                    selected="favourite"
                    navController.navigate("Favourite")
                }
            }, modifier = Modifier.weight(1f)) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(
                        painter = painterResource(R.drawable.baseline_bookmark_border_24),
                        contentDescription = null,
                        tint = if (selected == "favourite") Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Favourites", fontSize = 15.sp, color =if (selected == "favourite") Color.Red else Color.Gray)
                }
            }


        }
    }, snackbarHost = {
        SnackbarHost(hostState = snakState) })


    {paddingvalues->
        Surface(modifier = Modifier.fillMaxSize().padding(paddingvalues), color = MaterialTheme.colorScheme.background) {
//   Nav Host
            NavHost(navController = navController, startDestination = "Home")
            {
                composable("Home") {
                    Home(home, paddingvalues, snakState)
                }
                composable("Search") {
                    Search(default, filter, paddingvalues, snakState)
                }
                composable("Favourite") {
                    Library(paddingvalues,favViewmodel)
                }


            }
        }
        val context = LocalContext.current

        var backPressTime by remember { mutableStateOf(0L) }
        BackHandler {

            if (selected!="home") {
                selected="home"
               navController.navigate("Home") {
                    popUpTo(0) // Clear back stack
                }
            } else {
                if (System.currentTimeMillis() - backPressTime < 2000) {
                    (context as Activity).finish()
                } else {
                    Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    backPressTime = System.currentTimeMillis()
                }

            }
        }
    }

}