package com.grupo.albumfigurinhas.data.repository

import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.PlayerStats
import com.grupo.albumfigurinhas.data.model.Team

object DemoData {
    val competition = Competition(
        name = "Copa do Mundo",
        edition = "2026",
        teams = listOf(
            Team(
                id = "brasil",
                name = "Brasil",
                victories = 5,
                description = "Dados demonstrativos para o desenvolvimento da tela da equipe.",
                colors = listOf("#FFDF00", "#009C3B", "#002776"),
                players = listOf(
                    player("brasil-10", "Neymar", "Atacante", 10, 79, 55, 128),
                    player("brasil-7", "Vinicius Jr.", "Atacante", 7, 6, 5, 35),
                    player("brasil-5", "Casemiro", "Meio-campo", 5, 7, 3, 75),
                    player("brasil-1", "Alisson", "Goleiro", 1, 0, 0, 71),
                    player("brasil-3", "Marquinhos", "Zagueiro", 3, 7, 2, 95),
                ),
                coach = Coach(
                    id = "brasil-coach",
                    name = "Treinador do Brasil",
                    description = "Perfil demonstrativo do treinador.",
                ),
            ),
            Team(
                id = "argentina",
                name = "Argentina",
                victories = 3,
                description = "Dados demonstrativos para validar listas e navegacao.",
                colors = listOf("#74ACDF", "#FFFFFF", "#F6B40E"),
                players = listOf(
                    player("argentina-10", "Jogador 10", "Atacante", 10, 15, 9, 40),
                    player("argentina-9", "Jogador 9", "Atacante", 9, 12, 4, 33),
                    player("argentina-7", "Jogador 7", "Meio-campo", 7, 5, 11, 38),
                    player("argentina-4", "Jogador 4", "Zagueiro", 4, 2, 1, 41),
                    player("argentina-1", "Jogador 1", "Goleiro", 1, 0, 0, 36),
                ),
                coach = Coach(
                    id = "argentina-coach",
                    name = "Treinador da Argentina",
                    description = "Perfil demonstrativo do treinador.",
                ),
            ),
        ),
    )

    private fun player(
        id: String,
        name: String,
        position: String,
        number: Int,
        goals: Int,
        assists: Int,
        matches: Int,
    ) = Player(
        id = id,
        name = name,
        position = position,
        number = number,
        stats = PlayerStats(goals = goals, assists = assists, matches = matches),
    )
}
