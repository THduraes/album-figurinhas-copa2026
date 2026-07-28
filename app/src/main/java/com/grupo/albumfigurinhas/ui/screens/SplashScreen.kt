package com.grupo.albumfigurinhas.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo.albumfigurinhas.R
import com.grupo.albumfigurinhas.ui.theme.AlbumFigurinhasTheme

private val SplashPoppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
)

private val SplashGreen = Color(0xFF075A3B)
private val SplashGreenDark = Color(0xFF043A2A)
private val SplashYellow = Color(0xFFFFDC0B)
private val SplashWhite = Color(0xFFF9FBF8)

private data class SplashBadge(
    val drawable: Int,
    val name: String,
    val featured: Boolean = false,
)

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "splash-animation")
    val heroScale by transition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "hero-scale",
    )
    val trophyOffset by transition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "trophy-offset",
    )
    val progress by transition.animateFloat(
        initialValue = 0.08f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_650, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "loading-progress",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SplashGreenDark),
    ) {
        TeamBackgroundMosaic()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC032D22)),
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(SplashYellow),
        )

        SplashColorBand(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "COPA DO MUNDO 2026",
                color = SplashYellow,
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.weight(0.8f))

            Image(
                painter = painterResource(R.drawable.ic_trofeu),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .graphicsLayer {
                        translationY = trophyOffset
                    },
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "ALBUM DE FIGURINHAS",
                color = SplashWhite,
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "COPA 2026",
                color = SplashYellow,
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "SELECOES  |  CRAQUES  |  HISTORIAS",
                color = SplashWhite.copy(alpha = 0.78f),
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(34.dp))

            SplashBadges(featuredScale = heroScale)

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Carregando o album...",
                color = SplashWhite,
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = SplashYellow,
                trackColor = SplashWhite.copy(alpha = 0.22f),
                gapSize = 0.dp,
                drawStopIndicator = {},
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "PREPARANDO SEU ALBUM",
                color = SplashWhite.copy(alpha = 0.62f),
                fontFamily = SplashPoppins,
                fontWeight = FontWeight.Normal,
                fontSize = 9.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun TeamBackgroundMosaic() {
    val backgrounds = listOf(
        R.drawable.fundo_time_brasil,
        R.drawable.fundo_time_franca,
        R.drawable.fundo_time_cabo_verde,
        R.drawable.fundo_time_japao,
        R.drawable.fundo_time_eua,
    )

    Column(modifier = Modifier.fillMaxSize()) {
        backgrounds.forEach { background ->
            Image(
                painter = painterResource(background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}

@Composable
private fun SplashColorBand(modifier: Modifier = Modifier) {
    val colors = listOf(
        SplashYellow,
        Color(0xFFE42336),
        Color(0xFF1D5E9E),
        Color(0xFFF7F7F7),
        Color(0xFF244A9A),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(5.dp),
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color),
            )
        }
    }
}

@Composable
private fun SplashBadges(featuredScale: Float) {
    val badges = listOf(
        SplashBadge(R.drawable.escudo_franca, "Franca"),
        SplashBadge(R.drawable.escudo_cabo_verde, "Cabo Verde"),
        SplashBadge(R.drawable.escudo_brasil, "Brasil", featured = true),
        SplashBadge(R.drawable.escudo_japao, "Japao"),
        SplashBadge(R.drawable.escudo_eua, "Estados Unidos"),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        badges.forEach { badge ->
            SplashBadgeImage(
                badge = badge,
                size = if (badge.featured) 70.dp else 50.dp,
                scale = if (badge.featured) featuredScale else 1f,
            )
        }
    }
}

@Composable
private fun SplashBadgeImage(
    badge: SplashBadge,
    size: Dp,
    scale: Float,
) {
    Image(
        painter = painterResource(badge.drawable),
        contentDescription = "Escudo ${badge.name}",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
    )
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun SplashScreenPreview() {
    AlbumFigurinhasTheme {
        SplashScreen()
    }
}
