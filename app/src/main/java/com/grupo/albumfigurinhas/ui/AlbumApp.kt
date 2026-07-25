package com.grupo.albumfigurinhas.ui

import androidx.compose.runtime.Composable
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.navigation.AlbumNavGraph

@Composable
fun AlbumApp(repository: CompetitionRepository) {
    AlbumNavGraph(repository = repository)
}
