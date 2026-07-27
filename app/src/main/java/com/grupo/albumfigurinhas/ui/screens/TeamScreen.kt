package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.grupo.albumfigurinhas.R
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.ui.components.UiStateContent
import com.grupo.albumfigurinhas.ui.state.UiState

private val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
)

private data class TeamPersonUi(
    val id: String,
    val name: String,
    val subtitle: String,
    val photoModel: Any?,
    val isCoach: Boolean,
)

@Composable
fun TeamScreen(
    uiState: UiState<Team>,
    onBack: () -> Unit,
    onPlayerClick: (String) -> Unit,
    onCoachClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        UiStateContent(
            state = uiState,
            onRetry = onRetry,
            modifier = Modifier.fillMaxSize(),
        ) { team ->
            TeamContent(
                team = team,
                onBack = onBack,
                onPlayerClick = onPlayerClick,
                onCoachClick = onCoachClick,
            )
        }
    }
}

@Composable
private fun TeamContent(
    team: Team,
    onBack: () -> Unit,
    onPlayerClick: (String) -> Unit,
    onCoachClick: (String) -> Unit,
) {
    val teamGreen = if (team.id == "brasil") {
        Color(0xFF116A2D)
    } else {
        team.colors.getOrNull(1)?.toComposeColor()
            ?: MaterialTheme.colorScheme.primary
    }

    val teamYellow = if (team.id == "brasil") {
        Color(0xFFFFDC0B)
    } else {
        team.colors.firstOrNull()?.toComposeColor()
            ?: MaterialTheme.colorScheme.secondary
    }

    val people = remember(team) {
        val players = team.players.take(5).map { player ->
            TeamPersonUi(
                id = player.id,
                name = player.name,
                subtitle = "${player.number} - ${player.position}",
                photoModel = player.photo
                    .takeIf { it.isNotBlank() }
                    ?: localPlayerImage(player.id),
                isCoach = false,
            )
        }

        players + TeamPersonUi(
            id = team.coach.id,
            name = team.coach.name.substringAfterLast(" "),
            subtitle = "Treinador",
            photoModel = team.coach.photo
                .takeIf { it.isNotBlank() }
                ?: localCoachImage(team.coach.id),
            isCoach = true,
        )
    }

    val rows = remember(people) {
        people.chunked(3)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
    ) {
        item {
            TeamHeader(
                team = team,
                green = teamGreen,
                yellow = teamYellow,
                onBack = onBack,
            )
        }

        item {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                ),
            ) {
                Text(
                    text = "SOBRE A EQUIPE",
                    color = teamGreen,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = team.description,
                    color = Color(0xFF303030),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ELENCO",
                    color = teamGreen,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        items(
            items = rows,
            key = { row -> row.joinToString { it.id } },
        ) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 22.dp,
                    ),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.Top,
            ) {
                row.forEach { person ->
                    PersonCard(
                        person = person,
                        accentColor = teamGreen,
                        onClick = {
                            if (person.isCoach) {
                                onCoachClick(person.id)
                            } else {
                                onPlayerClick(person.id)
                            }
                        },
                        modifier = Modifier.weight(1f),
                    )
                }

                repeat(3 - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TeamHeader(
    team: Team,
    green: Color,
    yellow: Color,
    onBack: () -> Unit,
) {
    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }

    val badgeModel: Any? = team.badge
        .takeIf { it.isNotBlank() }
        ?: localTeamBadge(team.id)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(393f / 321f)
            .background(green),
    ) {
        if (team.id == "brasil") {
            Image(
                painter = painterResource(R.drawable.fundo_time_brasil),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(start = 10.dp, top = 4.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
                modifier = Modifier.size(30.dp),
            )
        }

        IconButton(
            onClick = { isFavorite = !isFavorite },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(end = 10.dp, top = 4.dp),
        ) {
            Icon(
                imageVector = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favoritar equipe",
                tint = Color.White,
                modifier = Modifier.size(30.dp),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 28.dp,
                    end = 24.dp,
                    top = 76.dp,
                    bottom = 34.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (badgeModel != null) {
                AsyncImage(
                    model = badgeModel,
                    contentDescription = "Escudo de ${team.name}",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(0.9f)
                        .fillMaxSize(),
                )
            } else {
                Box(
                    modifier = Modifier.weight(0.9f),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Escudo não disponível",
                        tint = Color.White,
                        modifier = Modifier.size(110.dp),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1.55f)
                    .padding(start = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = team.name.uppercase(),
                    color = Color.White,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "${team.victories} TÍTULOS",
                    color = yellow,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                TitleBadges(
                    teamId = team.id,
                    victories = team.victories,
                )
            }
        }
    }
}

@Composable
private fun TitleBadges(
    teamId: String,
    victories: Int,
) {
    val years = localTitleYears(teamId)

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top,
    ) {
        if (years.isNotEmpty()) {
            years.forEach { year ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_trofeu),
                        contentDescription = "Título de $year",
                        modifier = Modifier.size(27.dp),
                    )

                    Text(
                        text = year,
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.sp,
                    )
                }
            }
        } else {
            repeat(victories.coerceAtLeast(0)) {
                Image(
                    painter = painterResource(R.drawable.ic_trofeu),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp),
                )
            }
        }
    }
}

@Composable
private fun PersonCard(
    person: TeamPersonUi,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = if (accentColor.luminance() > 0.5f) {
        Color.Black
    } else {
        Color.White
    }

    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = accentColor,
        ),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Image(
                    painter = painterResource(R.drawable.fundo_elenco),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                if (person.photoModel != null) {
                    AsyncImage(
                        model = person.photoModel,
                        contentDescription = "Foto de ${person.name}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto não disponível",
                        tint = Color.White,
                        modifier = Modifier.size(54.dp),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(accentColor)
                    .padding(
                        horizontal = 4.dp,
                        vertical = 7.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = person.name.uppercase(),
                    color = textColor,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = person.subtitle,
                    color = textColor.copy(alpha = 0.9f),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 7.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

private fun String.toComposeColor(): Color? {
    return runCatching {
        Color(android.graphics.Color.parseColor(this))
    }.getOrNull()
}

private fun localTeamBadge(teamId: String): Int? {
    return when (teamId) {
        "brasil" -> R.drawable.escudo_brasil
        else -> null
    }
}

private fun localPlayerImage(playerId: String): Int? {
    return when (playerId) {
        "brasil-1" -> R.drawable.alisson_elenco
        "brasil-3" -> R.drawable.marquinhos_elenco
        "brasil-7" -> R.drawable.vini_elenco
        "brasil-10" -> R.drawable.neymar_elenco
        "brasil-19" -> R.drawable.endrick_elenco
        else -> null
    }
}

private fun localCoachImage(coachId: String): Int? {
    return when (coachId) {
        "brasil-coach" -> R.drawable.ancelotti_elenco
        else -> null
    }
}

private fun localTitleYears(teamId: String): List<String> {
    return when (teamId) {
        "brasil" -> listOf("1958", "1962", "1970", "1994", "2002")
        else -> emptyList()
    }
}