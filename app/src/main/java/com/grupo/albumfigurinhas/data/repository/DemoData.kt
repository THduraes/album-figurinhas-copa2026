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
                description = "A Seleção Brasileira é a maior vencedora da história das Copas do Mundo, com cinco títulos. Conhecida pelo futebol ofensivo e pela habilidade de seus jogadores, revelou lendas como Pelé, Ronaldo e Neymar. É reconhecida mundialmente por sua tradição e paixão pelo esporte.",
                colors = listOf("#FFDF00", "#009C3B", "#002776"),
                players = listOf(
                    player("brasil-10", "Neymar Jr.", "Atacante", 10, 79, 55, 128),
                    player("brasil-3", "Marquinhos", "Zagueiro", 3, 7, 2, 95),
                    player("brasil-7", "Vinicius Jr.", "Atacante", 7, 6, 5, 35),
                    player("brasil-19", "Endrick", "Atacante", 19, 3, 0, 14),
                    player("brasil-1", "Alisson", "Goleiro", 1, 0, 0, 71),
                ),
                coach = Coach(
                    id = "brasil-coach",
                    name = "Carlo Ancelotti",
                    description = "Treinador da Seleção Brasileira.",
                ),
            ),
            Team(
                id = "franca",
                name = "França",
                victories = 2,
                description = "A Seleção Francesa é uma das potências do futebol mundial, com dois títulos da Copa do Mundo. Destaca-se pela combinação de técniRTca, velocidade e força física. Ao longo dos anos, contou com grandes jogadores como Zidane, Henry e Mbappé.",
                colors = listOf("#232763", "#D91C20"),
                players = listOf(
                    player("franca-10", "Mbappé", "Atacante", 10, 52, 34, 93),
                    player("franca-7", "Dembélé", "Atacante", 7, 7, 9, 55),
                    player("franca-14", "Doué", "Atacante", 14, 1, 1, 8),
                    player("franca-11", "Olise", "Meio-campo", 11, 5, 7, 15),
                    player("franca-16", "Maignan", "Goleiro", 16, 0, 0, 35),
                ),
                coach = Coach(
                    id = "franca-coach",
                    name = "Didier Deschamps",
                    description = "Treinador da Seleção Francesa.",
                ),
            ),
            Team(
                id = "cabo-verde",
                name = "Cabo Verde",
                victories = 0,
                description = "A Seleção de Cabo Verde representa um dos países africanos que mais cresceram no cenário internacional. Conhecida pela organização tática e espírito de equipe, vem conquistando espaço em competições continentais. Sua evolução reflete o desenvolvimento do futebol no país.",
                colors = listOf("#022C5C", "#DA2631"),
                players = listOf(
                    player("cabo-verde-1", "Vozinha", "Goleiro", 1, 0, 0, 60),
                    player("cabo-verde-4", "Duarte", "Zagueiro", 4, 1, 0, 25),
                    player("cabo-verde-9", "Livramento", "Atacante", 9, 4, 2, 18),
                    player("cabo-verde-20", "Ryan Mendes", "Atacante", 20, 20, 8, 80),
                    player("cabo-verde-11", "Benchimol", "Atacante", 11, 3, 1, 15),
                ),
                coach = Coach(
                    id = "cabo-verde-coach",
                    name = "Bubista",
                    description = "Treinador da Seleção de Cabo Verde.",
                ),
            ),
            Team(
                id = "japao",
                name = "Japão",
                victories = 0,
                description = "A Seleção Japonesa é uma das mais fortes da Ásia e referência em disciplina tática. Destaca-se pela velocidade, organização e trabalho coletivo. Participa regularmente de competições internacionais e tem ampliado sua relevância no futebol mundial.",
                colors = listOf("#0E131A", "#D70D18"),
                players = listOf(
                    player("japao-10", "Ritsu Doan", "Meio-campo", 10, 10, 8, 57),
                    player("japao-4", "Ko Itakura", "Zagueiro", 4, 3, 2, 40),
                    player("japao-13", "Keito Nakamura", "Atacante", 13, 8, 4, 20),
                    player("japao-23", "Zion Suzuki", "Goleiro", 23, 0, 0, 20),
                    player("japao-9", "Ayase Ueda", "Atacante", 9, 14, 3, 30),
                ),
                coach = Coach(
                    id = "japao-coach",
                    name = "Hajime Moriyasu",
                    description = "Treinador da Seleção Japonesa.",
                ),
            ),
            Team(
                id = "eua",
                name = "EUA",
                victories = 0,
                description = "A Seleção dos Estados Unidos tem se consolidado como uma das principais forças do futebol na América do Norte. Conhecida pela intensidade física e competitividade, investe fortemente no desenvolvimento de jovens talentos. Seu crescimento tem aumentado sua presença em torneios internacionais.",
                colors = listOf("#1F2742", "#BB2533"),
                players = listOf(
                    player("eua-10", "Christian Pulisic", "Atacante", 10, 32, 20, 78),
                    player("eua-8", "Weston McKennie", "Meio-campo", 8, 11, 10, 60),
                    player("eua-20", "Folarin Balogun", "Atacante", 20, 6, 3, 20),
                    player("eua-2", "Sergiño Dest", "Lateral", 2, 3, 6, 34),
                    player("eua-1", "Matt Freese", "Goleiro", 1, 0, 0, 10),
                ),
                coach = Coach(
                    id = "eua-coach",
                    name = "Mauricio Pochettino",
                    description = "Treinador da Seleção dos Estados Unidos.",
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
