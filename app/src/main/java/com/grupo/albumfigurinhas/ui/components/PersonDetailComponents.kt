package com.grupo.albumfigurinhas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.grupo.albumfigurinhas.data.model.Team

private val BadgeColor = Color(0xFFFFC107)
private val BadgeTextColor = Color(0xFF1B1B1B)
private val HeroHeight = 320.dp

/**
 * Cor de destaque da equipe usada como fundo das telas de jogador/treinador.
 * Escolhe a cor mais escura da paleta do time (evitando tons quase-brancos)
 * para manter o texto branco legivel; sem paleta, usa [fallback].
 */
fun teamAccentColor(team: Team, fallback: Color): Color {
    val candidates = team.colors.mapNotNull { hex ->
        runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrNull()
    }.filterNot { it.luminance() > 0.82f }
    return candidates.minByOrNull { it.luminance() } ?: fallback
}

@Composable
fun DetailHeroHeader(
    photoUrl: String,
    accentColor: Color,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth().height(HeroHeight)) {
        if (photoUrl.isBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(accentColor.copy(alpha = 0.55f), accentColor),
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.35f),
                    modifier = Modifier.size(120.dp),
                )
            }
        } else {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.55f to Color.Transparent,
                        1f to accentColor,
                    ),
                ),
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.35f), CircleShape),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun DetailTag(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(icon, contentDescription = null, tint = BadgeColor, modifier = Modifier.size(16.dp))
        Text(
            text = text.uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp,
        )
    }
}

@Composable
fun NumberBadge(number: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(BadgeColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            color = BadgeTextColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DetailInfoCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        content = content,
    )
}

@Composable
fun StatRow(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}
