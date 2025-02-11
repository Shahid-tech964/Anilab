package com.example.anilab.Screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anilab.InfoActivity.AnimeDetail_Activity
import com.example.anilab.R
import com.example.anilab.Room.fav_Table
import com.example.anilab.Viewmodel.fav_viewmodel
import com.example.anilab.model_class.Data


@Composable
fun Library(paddingValues: PaddingValues,favViewmodel: fav_viewmodel) {
    val context= LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Library",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 18.dp, start = 10.dp, bottom = 13.dp)
        )

        val table_item by  favViewmodel.rows.observeAsState()

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {

           items(table_item?: emptyList()){

      SingleItems(it, onclick = {
          val intent= Intent(context, AnimeDetail_Activity::class.java)
          intent.putExtra("LargeImage",it.large_image)
          intent.putExtra("SmallImage",it.smal_img )
          intent.putExtra("Name",it.name )
          intent.putExtra("Genre",it.genre )
          intent.putExtra("Synopsis", it.synopsis)
          intent.putExtra("Rating",it.rating)
          intent.putExtra("Status", it.status)
          intent.putExtra("Episode", it.episode)
          intent.putExtra("Release", it.release)
          context.startActivity(intent)
      },favViewmodel)
           }


        }
    }
}

@Composable
fun SingleItems(item: fav_Table,onclick:()->Unit,favViewmodel: fav_viewmodel) {
    val name=item.name
    val imageUrl = item.smal_img
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

            IconButton(onClick = {
       favViewmodel.deleteItem(item.id)
            }, modifier = Modifier.align(Alignment.TopEnd).offset(13.dp, (-17).dp)) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(25.dp), tint = Color.White)
            }

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

