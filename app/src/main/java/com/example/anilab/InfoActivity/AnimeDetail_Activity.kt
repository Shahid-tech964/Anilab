package com.example.anilab.InfoActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.anilab.R
import com.example.anilab.Viewmodel.fav_viewmodel
import com.example.anilab.ui.theme.AnilabTheme
import com.example.anilab.ui.theme.Black


class AnimeDetail_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val fav_instance= ViewModelProvider(this).get(fav_viewmodel::class.java)

//        Intent data receiving
        val name=intent.getStringExtra("Name") ?:"Title Unavailable"
        val lar_img=intent.getStringExtra("LargeImage")?:"https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"
        val sma_img=intent.getStringExtra("SmallImage")?:"https://dummyimage.com/1920x1080/cccccc/000000.jpg&text=Image+Not+Available"
        val genre=intent.getStringExtra("Genre") ?:"Genre Unavailable"
        val synopsis=intent.getStringExtra("Synopsis") ?:"Synopsis Unavailable"

        val release=intent.getIntExtra("Release",0)
        val rating=intent.getFloatExtra("Rating", 0.0f)
        val  status=intent.getStringExtra("Status") ?:" Unavailable"
        val  episode=intent.getIntExtra("Episode",0)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor= Color.Transparent.toArgb()
        window.navigationBarColor= Color.Black.toArgb()

        setContent {
            AnilabTheme {

                Surface(modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(), color = MaterialTheme.colorScheme.background) {
                    ShowDetails(
                        name,
                        lar_img,
                        sma_img,
                        genre,
                        synopsis,
                        release,
                        rating,
                        status,
                        episode
                        ,fav_instance
                    )
                }
                }
            }
        }
    }


@Composable
fun ShowDetails(name :String,la_img:String,sma_img:String,genre:String,synopsis:String,release :Int,rating :Float,status:String,episode:Int,favViewmodel: fav_viewmodel) {

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
//        Large image
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)) {
            AsyncImage(
                model = la_img,
                contentDescription = "Anime image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .alpha(alpha = 0.8f)
                    .fillMaxSize()

            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                0.8f to Black.copy(alpha = 1f)
                            ),

                            )
                    )
            )

//            small image
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 20.dp, y = (60).dp)
            ) {
                AsyncImage(
                    model = sma_img,
                    contentDescription = "Anime image",
                    contentScale = ContentScale.Crop
                    , modifier = Modifier
                        .fillMaxSize()
                        .alpha(alpha = 0.9f)
                )
            }

//            heart icon
            IconButton(onClick ={favViewmodel.addItem(la_img,sma_img,name,genre,synopsis,rating,release,status,episode)}, modifier = Modifier.align(Alignment.BottomEnd) ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(30.dp))
            }

        }


        Spacer(modifier = Modifier.height(68.dp))
//        Title
        Text(text =name, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.onBackground )


        Spacer(modifier = Modifier.height(5.dp))
//        Genre
        Text(text =genre, fontSize = 17.sp,modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.onBackground )

        Spacer(modifier = Modifier.height(15.dp))
//        other info
        otherinfo(release,rating,status,episode)

        Spacer(modifier = Modifier.height(8.dp))
//        synopsis
        Synopsis(synopsis)


    }

}

//Synopsis composable

@Composable
fun Synopsis(synopsis: String) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

 Column(modifier = Modifier.padding(horizontal = 8.dp))  {
     AnimatedContent(targetState = isExpanded, label = "") { expand->
         Text(
             text = if (expand) synopsis else synopsis.take(163) + "......",
             style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground
         )
     }
     TextButton(onClick = {isExpanded=!isExpanded}, modifier = Modifier.align(Alignment.End)) {
         Text(text = if (isExpanded) "[Read Less]" else "[Read More]", color = Color.Gray,)
     }
 }

}

//other info composable
@Composable
fun otherinfo(release :Int,rating :Float,status:String,episode:Int) {



        Row(modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .height(40.dp), verticalAlignment = Alignment.CenterVertically) {

            Image(painter = painterResource(R.drawable.imdb),contentDescription = null,Modifier.size(37.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "$rating", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground  )

            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "$episode", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground )
            Spacer(modifier = Modifier.width(15.dp))

            Text(text = status, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "$release", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground )
        }



}


