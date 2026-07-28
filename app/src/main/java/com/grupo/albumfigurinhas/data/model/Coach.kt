package com.grupo.albumfigurinhas.data.model

data class Coach(
    val id: String = "",
    val name: String = "",
    val photo: String = "",
    val description: String = "",
    val birthDate: String = "",
    val stats: CoachStats? = null,
)
