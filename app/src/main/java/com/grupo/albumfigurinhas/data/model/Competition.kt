package com.grupo.albumfigurinhas.data.model

data class Competition(
    val name: String = "",
    val edition: String = "",
    val trophyImage: String = "",
    val teams: List<Team> = emptyList(),
)
