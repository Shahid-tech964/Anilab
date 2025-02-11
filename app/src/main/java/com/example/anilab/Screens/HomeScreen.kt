package com.example.anilab.Screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anilab.InfoActivity.AnimeDetail_Activity
import com.example.anilab.R
import com.example.anilab.StateHandling.HomeState
import com.example.anilab.Viewmodel.HomeViewmodel


import com.example.anilab.model_class.Data
import com.example.anilab.ui.theme.Black
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
 fun Home(home:HomeViewmodel,paddingValues: PaddingValues,snackbarHostState: SnackbarHostState) {
    // calling  viewmodel State Method

    LaunchedEffect(key1 = true) {
        home.getState()
    }
    val scope= rememberCoroutineScope()

    val uiState by home.result.observeAsState()



    //handling differnt states
    when(uiState){

//    failure state
        is HomeState.failure ->{
            val error_data=(uiState as HomeState.failure).error

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.error), contentDescription = null, modifier = Modifier.size(60.dp), tint = Color.Gray)
                Text(text = error_data, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                TextButton(onClick = {
                    home.getState()
                }) { Text(text = "Retry", fontSize = 13.sp, color = Color.Red) }
            }

          LaunchedEffect(key1 = true) {
              snackbarHostState.showSnackbar(message = error_data, duration = SnackbarDuration.Long)

          }

        }


//

//         success state
        is HomeState.success -> {
//             typecasting to get actual data
            val fakedata=(uiState as HomeState.success).s_data

            val actualdata=fakedata.data

//             header for categorize anime list
            val headers= listOf("","Top Airing","Most Favourite","Most Popular","Movies")

//             converting list in chunks returns list of list <list<list<Data>>>
            val groupedAnime=actualdata.chunked(5)


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                groupedAnime.forEachIndexed {index,animelist->

                    if(index==0){
                        item {
                            SliderRow(animelist)
                        }
                    }
                    else {
                        item {
                            Rowitems(animelist, headers[index])
                        }
                    }
                }
            }

        }
        null -> {

        }
    }

}


//Horizontal Slider
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  SliderRow(item:List<Data>) {
    val context = LocalContext.current
    val pagerstate = rememberPagerState(initialPage = 0, pageCount = { item.size })
    val corutinescope = rememberCoroutineScope()


//    Automatic sliding
    LaunchedEffect(key1 = pagerstate.currentPage) {
        delay(4000)
        val nextpage = (pagerstate.currentPage + 1) % item.size
        corutinescope.launch {
            pagerstate.animateScrollToPage(
                nextpage, animationSpec = tween(
                    durationMillis = 700,
                    easing = LinearOutSlowInEasing
                )
            )
        }
    }


    HorizontalPager(
        state = pagerstate,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)

    ) { index ->
        val title = item[index].title_english ?: "Title is Unavailable"
        val imageUrl = item[index].images.jpg.large_image_url
            ?: "https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"

        val genreObject =item[index].genres
        val genreList:MutableList<String> = mutableListOf()

        genreObject.forEach { it->
            genreList.add(it.name)
        }

        val fakegenre =genreList.distinct()

        val horiGenre=fakegenre.joinToString(" , ")
        val Intentgenre =fakegenre.joinToString(" / ")

        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                val intent = Intent(context, AnimeDetail_Activity::class.java)
                intent.putExtra("LargeImage", item[index].trailer.images.maximum_image_url)
                intent.putExtra("SmallImage", item[index].images.jpg.large_image_url)
                intent.putExtra("Name", item[index].title_english)
                intent.putExtra("Genre", Intentgenre)
                intent.putExtra("Synopsis", item[index].synopsis)
                intent.putExtra("Rating", item[index].score.toFloat())
                intent.putExtra("Status", item[index].status)
                intent.putExtra("Episode", item[index].episodes)
                intent.putExtra("Release", item[index].year)
                context.startActivity(intent)
            })
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl) // The actual image URL
                    .placeholder(R.drawable.placeholder) // External placeholder URL
                    .build(),
                contentDescription = "AnimeImage",
                contentScale = ContentScale.Crop
                , modifier = Modifier
                    .alpha(alpha = 0.8f)
                    .fillMaxSize()


            )
            Text(
                text = if (title.length >=25) title.take(25) + "...." else title, fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 5.dp, y = (-70).dp),
                color = Color.White, fontWeight = FontWeight.Bold
            )

            Text(text = horiGenre, fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x= 5.dp, y=(-50).dp
                ))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                0.8f to Black.copy(alpha = 1f)
                            )


                        )

                    )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(x = 0.dp, y = (-20).dp), horizontalArrangement = Arrangement.Center) {
                repeat(item.size){index->
                    ScrollingIndicator(index==pagerstate.currentPage)
                }
            }
        }
    }
}

@Composable
fun ScrollingIndicator(slected:Boolean) {

    Box(modifier = Modifier
        .padding(3.dp)
        .background(color = if (slected) Color.White else Color.Gray, shape = CircleShape)
        .size(6.dp))

}








//Single row

@SuppressLint("SuspiciousIndentation")
@Composable
fun Rowitems(item:List<Data>, header:String) {

    val genrelist:MutableList<String> = mutableListOf()
    val context = LocalContext.current
    Column(modifier = Modifier.padding(top = 15.dp)) {

        Text(text = header, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(start = 6.dp, bottom = 3.dp))
        LazyRow (){

            items(item){ it ->

//               making list for genre
                it.genres.forEach{item->
                    genrelist.add(item.name)
                }

//    contains duplicate data
                val fakegenre =genrelist.distinct()

//              contains unique data
                val genre=fakegenre.joinToString(" / ")


                Singleitem(
                    it, onclick = {
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
                    }

                )
            }
        }
    }
}


//Single card
@Composable
fun Singleitem(oneitem: Data, onclick:()->Unit) {

    val name=oneitem.title_english?:"Title Unavailable"
    val imageUrl = oneitem.images.jpg.large_image_url ?: "https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"
    Card(modifier = Modifier
        .padding(bottom = 13.dp, start = 10.dp, end = 10.dp) .width(150.dp)
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


            Box(modifier = Modifier.fillMaxWidth().height(50.dp).
            align(Alignment.BottomCenter).background(Brush.verticalGradient(
                colorStops = arrayOf(
                    0f to Color.Transparent,
                    0.7f to Color.Black.copy(0.4f)
                )
            ))) {
                Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier =Modifier.align(
                    Alignment.BottomStart) , color = Color.White)

            }

        }
    }


}

