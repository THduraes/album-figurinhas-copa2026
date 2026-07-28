package com.grupo.albumfigurinhas.ui.components

import androidx.annotation.DrawableRes
import com.grupo.albumfigurinhas.R
import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Player

fun playerCardPhotoModel(player: Player): Any? =
    player.thumbnail.takeIf(String::isRemotePhoto)
        ?: localPlayerThumbnail(player.name)
        ?: playerDetailPhotoModel(player)

fun playerDetailPhotoModel(player: Player): Any? =
    player.photo.takeIf(String::isRemotePhoto) ?: localPlayerPhoto(player.name)

fun coachCardPhotoModel(coach: Coach): Any? =
    coach.thumbnail.takeIf(String::isRemotePhoto)
        ?: localCoachThumbnail(coach.name)
        ?: coachDetailPhotoModel(coach)

fun coachDetailPhotoModel(coach: Coach): Any? =
    coach.photo.takeIf(String::isRemotePhoto) ?: localCoachPhoto(coach.name)

private fun String.isRemotePhoto(): Boolean =
    startsWith("https://") || startsWith("http://")

@DrawableRes
private fun localPlayerPhoto(name: String): Int? {
    val normalizedName = name.lowercase()
    return when {
        "neymar" in normalizedName -> R.drawable.player_neymar_jr
        "vinicius" in normalizedName -> R.drawable.player_vinicius_jr
        "marquinhos" in normalizedName -> R.drawable.player_marquinhos
        "endrick" in normalizedName -> R.drawable.player_endrick
        "alisson" in normalizedName -> R.drawable.player_alisson
        "mbappé" in normalizedName || "mbappe" in normalizedName -> R.drawable.player_mbappe
        "olise" in normalizedName -> R.drawable.player_olise
        "dembélé" in normalizedName || "dembele" in normalizedName -> R.drawable.player_dembele
        "doué" in normalizedName || "doue" in normalizedName -> R.drawable.player_doue
        "maignan" in normalizedName -> R.drawable.player_maignan
        "doan" in normalizedName -> R.drawable.player_doan
        "ueda" in normalizedName -> R.drawable.player_ueda
        "itakura" in normalizedName -> R.drawable.player_itakura
        "nakamura" in normalizedName -> R.drawable.player_nakamura
        "suzuki" in normalizedName -> R.drawable.player_suzuki
        "benchimol" in normalizedName -> R.drawable.player_benchimol
        "duarte" in normalizedName -> R.drawable.player_duarte
        "livramento" in normalizedName -> R.drawable.player_livramento
        "ryan mendes" in normalizedName -> R.drawable.player_ryan_mendes
        "vozinha" in normalizedName -> R.drawable.player_vozinha
        "pulisic" in normalizedName -> R.drawable.player_pulisic
        "dest" in normalizedName -> R.drawable.player_dest
        "balogun" in normalizedName -> R.drawable.player_balogun
        "mckennie" in normalizedName -> R.drawable.player_mckennie
        "freese" in normalizedName -> R.drawable.player_freese
        else -> null
    }
}

@DrawableRes
private fun localPlayerThumbnail(name: String): Int? {
    val normalizedName = name.lowercase()
    return when {
        "neymar" in normalizedName -> R.drawable.neymar_elenco
        "vinicius" in normalizedName -> R.drawable.vini_elenco
        "marquinhos" in normalizedName -> R.drawable.marquinhos_elenco
        "endrick" in normalizedName -> R.drawable.endrick_elenco
        "alisson" in normalizedName -> R.drawable.alisson_elenco
        "mbapp" in normalizedName -> R.drawable.mbappe_elenco
        "olise" in normalizedName -> R.drawable.olise_elenco
        "demb" in normalizedName -> R.drawable.dembele_elenco
        "dou" in normalizedName -> R.drawable.doue_elenco
        "maignan" in normalizedName -> R.drawable.maignan_elenco
        "doan" in normalizedName -> R.drawable.doan_elenco
        "ueda" in normalizedName -> R.drawable.ueda_elenco
        "itakura" in normalizedName -> R.drawable.itakura_elenco
        "nakamura" in normalizedName -> R.drawable.nakamura_elenco
        "suzuki" in normalizedName -> R.drawable.suzuki_elenco
        "benchimol" in normalizedName -> R.drawable.benchimol_elenco
        "duarte" in normalizedName -> R.drawable.duarte_elenco
        "livramento" in normalizedName -> R.drawable.livramento_elenco
        "ryan mendes" in normalizedName -> R.drawable.ryan_elenco
        "vozinha" in normalizedName -> R.drawable.vozinha_elenco
        "pulisic" in normalizedName -> R.drawable.pulisic_elenco
        "dest" in normalizedName -> R.drawable.dest_elenco
        "balogun" in normalizedName -> R.drawable.balogun_elenco
        "mckennie" in normalizedName -> R.drawable.mckennie_elenco
        "freese" in normalizedName -> R.drawable.freese_elenco
        else -> null
    }
}

@DrawableRes
private fun localCoachPhoto(name: String): Int? {
    val normalizedName = name.lowercase()
    return when {
        "ancelotti" in normalizedName -> R.drawable.ancelotti_elenco
        "deschamps" in normalizedName -> R.drawable.deschamps_elenco
        "bubista" in normalizedName -> R.drawable.bubista_elenco
        "moriyasu" in normalizedName -> R.drawable.hajime_elenco
        "pochettino" in normalizedName -> R.drawable.pochettino_elenco
        else -> null
    }
}

@DrawableRes
private fun localCoachThumbnail(name: String): Int? = localCoachPhoto(name)
