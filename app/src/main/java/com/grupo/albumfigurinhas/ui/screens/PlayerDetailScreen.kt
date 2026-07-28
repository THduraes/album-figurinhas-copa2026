package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grupo.albumfigurinhas.ui.components.DetailHeroHeader
import com.grupo.albumfigurinhas.ui.components.DetailInfoCard
import com.grupo.albumfigurinhas.ui.components.DetailTag
import com.grupo.albumfigurinhas.ui.components.NumberBadge
import com.grupo.albumfigurinhas.ui.components.StatRow
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.components.playerDetailPhotoModel
import com.grupo.albumfigurinhas.ui.components.teamAccentColor
import com.grupo.albumfigurinhas.ui.state.UiState
import com.grupo.albumfigurinhas.viewmodel.PlayerDetail

@Composable
fun PlayerDetailScreen(
    uiState: UiState<PlayerDetail>,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    UiStateContent(
        state = uiState,
        onRetry = onRetry,
        modifier = modifier.fillMaxSize(),
    ) { detail ->
        val accentColor = teamAccentColor(detail.team, fallback = MaterialTheme.colorScheme.primary)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(accentColor)
                .verticalScroll(rememberScrollState()),
        ) {
            DetailHeroHeader(
                photoModel = playerDetailPhotoModel(detail.player),
                accentColor = accentColor,
                onBack = onBack,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = detail.player.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            DetailTag(icon = Icons.Filled.Person, text = detail.player.position)
                            DetailTag(icon = Icons.Filled.Flag, text = detail.team.name)
                        }
                    }
                    NumberBadge(number = detail.player.number)
                }

                val stats = detail.player.stats
                val birthDate = detail.player.birthDate
                if (stats != null || birthDate.isNotBlank()) {
                    DetailInfoCard {
                        Text(
                            text = "Estatísticas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        stats?.let {
                            StatRow(Icons.Filled.SportsScore, "Jogos", it.matches.toString())
                            StatRow(Icons.Filled.SportsSoccer, "Gols", it.goals.toString())
                            StatRow(
                                Icons.AutoMirrored.Filled.TrendingUp,
                                "Assistências",
                                it.assists.toString(),
                            )
                        }
                        if (birthDate.isNotBlank()) {
                            StatRow(Icons.Filled.CalendarMonth, "Data de Nascimento", birthDate)
                        }
                    }
                }
            }
        }
    }
}
