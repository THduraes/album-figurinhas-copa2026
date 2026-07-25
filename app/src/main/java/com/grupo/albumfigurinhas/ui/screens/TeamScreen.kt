@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.state.UiState

@Composable
fun TeamScreen(
    uiState: UiState<Team>,
    onBack: () -> Unit,
    onPlayerClick: (String) -> Unit,
    onCoachClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Equipe") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
            )
        },
    ) { innerPadding ->
        UiStateContent(
            state = uiState,
            onRetry = onRetry,
            modifier = Modifier.padding(innerPadding),
        ) { team ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(team.name, style = MaterialTheme.typography.headlineMedium)
                        Text("${team.victories} titulos", style = MaterialTheme.typography.titleMedium)
                        Text(team.description, style = MaterialTheme.typography.bodyLarge)
                        Text("Elenco", style = MaterialTheme.typography.titleLarge)
                    }
                }
                items(team.players, key = Player::id) { player ->
                    PersonRow(
                        title = player.name,
                        subtitle = "${player.position} - camisa ${player.number}",
                        onClick = { onPlayerClick(player.id) },
                    )
                }
                item {
                    PersonRow(
                        title = team.coach.name,
                        subtitle = "Treinador",
                        onClick = { onCoachClick(team.coach.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonRow(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
