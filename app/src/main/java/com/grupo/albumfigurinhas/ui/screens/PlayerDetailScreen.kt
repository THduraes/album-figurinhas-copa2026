@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.state.UiState

@Composable
fun PlayerDetailScreen(
    uiState: UiState<Player>,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Jogador") },
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
        ) { player ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(player.name, style = MaterialTheme.typography.headlineMedium)
                Text("${player.position} - camisa ${player.number}")
                player.stats?.let { stats ->
                    Text("Gols: ${stats.goals}")
                    Text("Assistencias: ${stats.assists}")
                    Text("Partidas: ${stats.matches}")
                }
            }
        }
    }
}
