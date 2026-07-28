package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo.albumfigurinhas.R
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.state.UiState
private val GoldAccent = Color(0xFFFFC93C)
private val CardBackground = Color.White.copy(alpha = 0.08f)
private val SubtitleGray = Color(0xFFB8C0CC)
private val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_extra_bold, FontWeight.W900),
)

private val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

@Composable
fun CompetitionScreen(
    uiState: UiState<Competition>,
    onTeamClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_competicao),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        UiStateContent(
            state = uiState,
            onRetry = onRetry,
            modifier = Modifier.fillMaxSize(),
        ) { competition ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    CompetitionHeader(competition = competition)
                }
                item {
                    Text(
                        text = buildAnnotatedString {
                            append("SELECIONE UMA ")
                            withStyle(SpanStyle(color = GoldAccent)) {
                                append("SELEÇÃO")
                            }
                        },
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    )
                }
                items(competition.teams, key = Team::id) { team ->
                    TeamRow(team = team, onClick = { onTeamClick(team.id) })
                }
            }
        }
    }
}

@Composable
private fun CompetitionHeader(competition: Competition) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    append(competition.name.uppercase())
                }
                append(" ")
                withStyle(SpanStyle(color = GoldAccent)) {
                    append(competition.edition)
                }
            },
            fontFamily = Poppins,
            fontWeight = FontWeight.W900,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(id = R.drawable.taca_tela_inicial),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .size(140.dp),
        )
    }
}

@Composable
private fun TeamRow(team: Team, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = teamBadgeRes(team.id)),
                contentDescription = team.name,
                modifier = Modifier.size(36.dp),
            )
            Text(
                text = team.name.uppercase(),
                color = Color.White,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_trofeu),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "${team.victories}",
                    color = GoldAccent,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = "TÍTULOS",
                    color = SubtitleGray,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                )
            }
        }
    }
}

private fun teamBadgeRes(teamId: String): Int = when (teamId) {
    "brasil" -> R.drawable.escudo_brasil
    "franca" -> R.drawable.escudo_franca
    "cabo-verde" -> R.drawable.escudo_cabo_verde
    "japao" -> R.drawable.escudo_japao
    "eua" -> R.drawable.escudo_eua
    else -> R.drawable.ic_trofeu
}