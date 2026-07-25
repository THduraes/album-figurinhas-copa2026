package com.grupo.albumfigurinhas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.grupo.albumfigurinhas.ui.AlbumApp
import com.grupo.albumfigurinhas.ui.theme.AlbumFigurinhasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = (application as AlbumApplication).container.competitionRepository
        setContent {
            AlbumFigurinhasTheme {
                AlbumApp(repository = repository)
            }
        }
    }
}
