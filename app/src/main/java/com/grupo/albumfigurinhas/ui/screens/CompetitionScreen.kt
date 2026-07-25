@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.state.UiState

@Composable
fun CompetitionScreen(
    uiState: UiState<Competition>,
    onTeamClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Album da competicao") }) },
    ) { innerPadding ->
        UiStateContent(
            state = uiState,
            onRetry = onRetry,
            modifier = Modifier.padding(innerPadding),
        ) { competition ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(competition.name, style = MaterialTheme.typography.headlineMedium)
                        Text(competition.edition, style = MaterialTheme.typography.titleLarge)
                    }
                }
                items(competition.teams, key = Team::id) { team ->
                    TeamRow(team = team, onClick = { onTeamClick(team.id) })
                }
            }
        }
    }
}

@Composable
private fun TeamRow(team: Team, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(team.name, style = MaterialTheme.typography.titleMedium)
            Text("${team.victories} titulos", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
