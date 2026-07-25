package com.grupo.albumfigurinhas.data.model

data class Player(
    val id: String = "",
    val name: String = "",
    val position: String = "",
    val number: Int = 0,
    val photo: String = "",
    val stats: PlayerStats? = null,
)
