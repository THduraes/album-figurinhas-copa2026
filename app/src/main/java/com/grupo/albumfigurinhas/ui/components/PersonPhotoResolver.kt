package com.grupo.albumfigurinhas.ui.components

import androidx.annotation.DrawableRes
import com.grupo.albumfigurinhas.R
import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Player

fun playerPhotoModel(player: Player): Any? =
    player.photo.takeIf(String::isRemotePhoto) ?: localPlayerPhoto(player.name)

fun coachPhotoModel(coach: Coach): Any? =
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
private fun localCoachPhoto(name: String): Int? {
    val normalizedName = name.lowercase()
    return when {
        "ancelotti" in normalizedName -> R.drawable.coach_ancelotti
        "deschamps" in normalizedName -> R.drawable.deschamps_elenco
        "bubista" in normalizedName -> R.drawable.bubista_elenco
        "moriyasu" in normalizedName -> R.drawable.hajime_elenco
        "pochettino" in normalizedName -> R.drawable.pochettino_elenco
        else -> null
    }
}
