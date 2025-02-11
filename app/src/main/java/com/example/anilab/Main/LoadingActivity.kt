package com.example.anilab.Main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.anilab.StateHandling.loadState
import com.example.anilab.Viewmodel.LoadingViewmodel
import com.example.anilab.ui.theme.AnilabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewmodel:LoadingViewmodel by viewModels()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor= Color.Transparent.toArgb()
        window.navigationBarColor= Color.Black.toArgb()
        setContent {
            AnilabTheme {
                Surface( color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
                    statehandle(viewmodel)
                }
            }
        }
    }

    @Composable
    fun statehandle(viewmodel: LoadingViewmodel) {
        val context=this
        LaunchedEffect(key1 = true) {
            viewmodel.getState()
        }

        val state by viewmodel.result.observeAsState()

        when(state){
            loadState.dataloaded -> {
                val intent= Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                context.finish()
            }
            loadState.loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }
            null -> {

            }
        }

    }
}

