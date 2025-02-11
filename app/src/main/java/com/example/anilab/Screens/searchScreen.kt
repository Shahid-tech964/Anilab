package com.example.anilab.Screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anilab.InfoActivity.AnimeDetail_Activity
import com.example.anilab.R
import com.example.anilab.StateHandling.DefualtState
import com.example.anilab.StateHandling.HomeState
import com.example.anilab.StateHandling.SearchStates
import com.example.anilab.Viewmodel.DefaultSearchViewmodel
import com.example.anilab.Viewmodel.filterViewmodel


import com.example.anilab.model_class.Data

@Composable
fun Search(default:DefaultSearchViewmodel,filter:filterViewmodel,paddingValues: PaddingValues,snackbarHostState: SnackbarHostState) {
    var title by rememberSaveable  {
        mutableStateOf("")
    }

    var  bool by rememberSaveable  {
        mutableStateOf(true)
    }



    Column(modifier = Modifier.fillMaxSize()) {

//Search field
        Box(modifier = Modifier
            .padding(top = 17.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
            .fillMaxWidth()
            .height(60.dp)) {

            TextField(value = title, onValueChange = {
                title = it

                if (title.isEmpty()){
                    bool=true
                }
            },
                modifier = Modifier
                    .fillMaxSize()
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
                placeholder = { Text(text = "Start Searching...", color = Color.Gray) },

                trailingIcon = {
                    IconButton(onClick = {

                        if (title.isNotEmpty()){
                            title=""
                            bool=true
                        }

                    }) {
                        Icon(
                            imageVector = if (title.isNotEmpty()) {Icons.Default.Clear} else {Icons.Default.Search},
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search // Changes the keyboard button to "Search"
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {

                     bool=   if (title.isNotEmpty())
                        {
                          filter.getState(title)
                            false
                        }
                        else{
                            true
                     }
                     }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    cursorColor = Color.Red
             , focusedTextColor = Color.White
                ), singleLine = true, shape = RoundedCornerShape(10.dp)
            )


        }

        if(bool){
            DefualtStates(default,paddingValues,snackbarHostState)
        }
        else{
            Searchgridstates(filter,paddingValues,snackbarHostState,title)
        }





    }

}


//managing default states of search screen

@Composable
fun DefualtStates(default: DefaultSearchViewmodel,paddingValues: PaddingValues,snackbarHostState: SnackbarHostState) {

    LaunchedEffect(key1 = true) {
        default.getState()
    }
    val state by default.result.observeAsState()
    when(state){

        is DefualtState.failure ->{
            val error_data=(state as DefualtState.failure).error

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.error), contentDescription = null, modifier = Modifier.size(60.dp), tint = Color.Gray)
                Text(text = error_data, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                TextButton(onClick = {
                    default.getState()
                }) { Text(text = "Retry", fontSize = 13.sp, color = Color.Red) }
            }

            LaunchedEffect(key1 = true) {
                snackbarHostState.showSnackbar(message = error_data, duration = SnackbarDuration.Long)

            }
        }

//        loading state
//                HomeState.loading -> {
//
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(color = Color.Red)
//            }
//
//
//        }

        is DefualtState.success -> {
            val response=(state as DefualtState.success).s_data

            val actualdata=response.data

            verticalLayout(actualdata,paddingValues)


        }
        null ->{

        }
    }
}


//contains set of data
@Composable
fun verticalLayout(data:List<Data>,paddingValues: PaddingValues) {
val context= LocalContext.current
    val genrelist:MutableList<String> = mutableListOf()

    LazyColumn(modifier = Modifier
        .fillMaxSize()) {

            items(data){
                it.genres.forEach{item->
                    genrelist.add(item.name)
                }

//    contains duplicate data
                val fakegenre =genrelist.distinct()

//              contains unique data
                val genre=fakegenre.joinToString(" / ")
                singleItem(it, onclick = {
                    val intent= Intent(context, AnimeDetail_Activity::class.java)
                    intent.putExtra("LargeImage",it.trailer.images.maximum_image_url)
                    intent.putExtra("SmallImage",it.images.jpg.large_image_url )
                    intent.putExtra("Name",it.title_english )
                    intent.putExtra("Genre",genre )
                    intent.putExtra("Synopsis", it.synopsis)
                    intent.putExtra("Rating",it.score.toFloat())
                    intent.putExtra("Status", it.status)
                    intent.putExtra("Episode", it.episodes)
                    intent.putExtra("Release", it.year)
                    context.startActivity(intent)
                })
            }
    }

}



// handling single data
@SuppressLint("SuspiciousIndentation")
@Composable
fun singleItem(item:Data,onclick: () -> Unit) {
    val imageUrl=item.images.jpg.large_image_url?:"https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"
    val title =item.title_english?:"Title  Unavailable "
       Row(modifier = Modifier.padding(bottom = 13.dp).fillMaxWidth().height(100.dp).clickable { onclick() }, verticalAlignment = Alignment.CenterVertically){

           Card(modifier = Modifier.padding(start = 10.dp).fillMaxHeight().width(120.dp)) {

               AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                   .data(imageUrl) // The actual image URL
                   .placeholder(R.drawable.placeholder) // External placeholder URL
                   .build(),  contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

           }

           Spacer(modifier = Modifier.width(20.dp))

           Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(10.dp))

       }
}








//handling filter state
@Composable
fun Searchgridstates(instance:filterViewmodel,paddingValues: PaddingValues,snackbarHostState: SnackbarHostState,title:String) {
    val state by instance.result.observeAsState()
    when(state){

        is SearchStates.Failure ->{
            val error_data=(state as SearchStates.Failure).error

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.error), contentDescription = null, modifier = Modifier.size(60.dp), tint = Color.Gray)
                Text(text = error_data, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                TextButton(onClick = {
                    instance.getState(title)
                }) { Text(text = "Retry", fontSize = 13.sp, color = Color.Red) }
            }

            LaunchedEffect(key1 = true) {
                snackbarHostState.showSnackbar(message = error_data, duration = SnackbarDuration.Long)

            }

        }


//         loading state
        SearchStates.loading -> {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }


        }
        is SearchStates.sucsess -> {
            val response=(state as SearchStates.sucsess).data

            val actualdata=response.data

            GridLayout(actualdata,paddingValues)


        }
        is SearchStates.Notfound ->{
            val empty=(state as SearchStates.Notfound).mess
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(painter = painterResource(R.drawable.notfound), contentDescription = null, modifier = Modifier.size(70.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.height(7.dp))
                Text(text = empty, fontSize = 18.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        }
        null ->{

        }


    }
}







//Grid layout

@Composable
fun GridLayout(items:List<Data>,paddingValues: PaddingValues) {
  val context= LocalContext.current
    val genrelist:MutableList<String>  = mutableListOf()
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        Modifier
            .fillMaxSize()) {


        items(items.take(20)){it->
            it.genres.forEach{item->
                genrelist.add(item.name)
            }

//    contains duplicate data
            val fakegenre =genrelist.distinct()

//              contains unique data
            val genre=fakegenre.joinToString(" / ")
            ShowItems(it,onclick={
                val intent= Intent(context, AnimeDetail_Activity::class.java)
                intent.putExtra("LargeImage",it.trailer.images.maximum_image_url)
                intent.putExtra("SmallImage",it.images.jpg.large_image_url )
                intent.putExtra("Name",it.title_english )
                intent.putExtra("Genre",genre )
                intent.putExtra("Synopsis", it.synopsis)
                intent.putExtra("Rating",it.score.toFloat())
                intent.putExtra("Status", it.status)
                intent.putExtra("Episode", it.episodes)
                intent.putExtra("Release", it.year)
                context.startActivity(intent)
            })
        }

    }

}






// single items
@Composable
fun ShowItems(item:Data,onclick:()->Unit) {
    val name=item.title_english?:"Title Unavailable"
    val imageUrl = item.images.jpg.large_image_url ?: "https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"
    Card(modifier = Modifier
        .padding(bottom = 13.dp, start = 10.dp, end = 10.dp)
        .width(150.dp)
        .height(200.dp)
        .clickable {
            onclick()
        }) {
        Box(modifier = Modifier
            .fillMaxSize() ) {

            AsyncImage( model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl) // The actual image URL
                .placeholder(R.drawable.placeholder) // External placeholder URL
                .build(), contentDescription = "AnimeImage",
                contentScale = ContentScale.Crop
                , modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = 0.9f))


            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.7f to Color.Black.copy(0.4f)
                        )
                    )
                )) {
                Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier =Modifier.align(
                    Alignment.BottomStart) , color = Color.White)

            }

        }
    }

}










