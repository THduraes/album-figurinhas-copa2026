package com.grupo.albumfigurinhas.data.model

data class Team(
    val id: String = "",
    val name: String = "",
    val victories: Int = 0,
    val description: String = "",
    val badge: String = "",
    val colors: List<String> = emptyList(),
    val players: List<Player> = emptyList(),
    val coach: Coach = Coach(),
)
