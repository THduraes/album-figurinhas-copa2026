package com.grupo.albumfigurinhas.data.repository

import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.CoachStats
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.PlayerStats
import com.grupo.albumfigurinhas.data.model.Team

/**
 * Dados de demonstracao para desenvolvimento sem Firebase. Selecoes, escalacoes
 * e estatisticas seguem os protótipos de design (Figma); jogadores sem card de
 * estatisticas detalhado no protótipo ficam com `stats = null` em vez de numeros
 * inventados. Fotos vem de recortes das telas do Figma, empacotados como
 * drawables (ver `res/drawable/player_*`), conforme sugerido no guia do grupo
 * para imagens fixas da interface.
 */
object DemoData {
    val competition = Competition(
        name = "Copa do Mundo",
        edition = "2026",
        teams = listOf(
            Team(
                id = "brasil",
                name = "Brasil",
                victories = 5,
                description = "A Seleção Brasileira é a maior vencedora da história das Copas " +
                    "do Mundo, com cinco títulos. Conhecida pelo futebol ofensivo e pela " +
                    "habilidade de seus jogadores, revelou lendas como Pelé, Ronaldo e Neymar. " +
                    "É reconhecida mundialmente por sua tradição e paixão pelo esporte.",
                colors = listOf("#116A2D", "#FFDF00", "#002776"),
                players = listOf(
                    player(
                        "brasil-10", "Neymar Jr", "Atacante", 10,
                        PlayerStats(79, 55, 128), "05/02/1992", photo = "neymar_jr",
                    ),
                    player(
                        "brasil-7", "Vinicius Jr.", "Atacante", 7,
                        PlayerStats(14, 5, 52), "12/07/2000", photo = "vinicius_jr",
                    ),
                    player("brasil-4", "Marquinhos", "Zagueiro", 4, photo = "marquinhos"),
                    player(
                        "brasil-19", "Endrick", "Atacante", 19,
                        PlayerStats(5, 1, 16), "21/07/2006", photo = "endrick",
                    ),
                    player(
                        "brasil-1", "Alisson", "Goleiro", 1,
                        PlayerStats(0, 0, 74), "02/10/1992", photo = "alisson",
                    ),
                ),
                coach = coach(
                    "brasil-coach", "Ancelotti",
                    CoachStats(wins = 9, losses = 3, matches = 15), "10/05/1959", photo = "ancelotti",
                ),
            ),
            Team(
                id = "franca",
                name = "França",
                victories = 2,
                description = "A Seleção Francesa é uma das potências do futebol mundial, com " +
                    "dois títulos da Copa do Mundo. Destaca-se pela combinação de técnica, " +
                    "velocidade e força física. Ao longo dos anos, contou com grandes " +
                    "jogadores como Zidane, Henry e Mbappé.",
                colors = listOf("#232763", "#0055A4", "#EF4135"),
                players = listOf(
                    player(
                        "franca-10", "Mbappé", "Atacante", 10,
                        PlayerStats(60, 40, 100), "20/12/1998", photo = "mbappe",
                    ),
                    player(
                        "franca-11", "Olise", "Atacante", 11,
                        PlayerStats(7, 9, 25), "12/12/2001", photo = "olise",
                    ),
                    player(
                        "franca-7", "Dembélé", "Atacante", 7,
                        PlayerStats(13, 6, 65), "15/05/1997", photo = "dembele",
                    ),
                    player(
                        "franca-20", "Doué", "Atacante", 20,
                        PlayerStats(3, 2, 15), "03/06/2005", photo = "doue",
                    ),
                    player(
                        "franca-18", "Maignan", "Goleiro", 18,
                        PlayerStats(0, 0, 47), "03/07/1995", photo = "maignan",
                    ),
                ),
                coach = Coach(id = "franca-coach", name = "Deschamps"),
            ),
            Team(
                id = "japao",
                name = "Japão",
                victories = 0,
                description = "A Seleção Japonesa é uma das mais fortes da Ásia e referência em " +
                    "disciplina tática. Destaca-se pela velocidade, organização e trabalho " +
                    "coletivo. Participa regularmente de competições internacionais e tem " +
                    "ampliado sua relevância no futebol mundial.",
                colors = listOf("#0E131A", "#BC002D", "#FFFFFF"),
                players = listOf(
                    player(
                        "japao-10", "Doan", "Atacante", 10,
                        PlayerStats(11, 9, 71), "16/06/1998", photo = "doan",
                    ),
                    player(
                        "japao-18", "Ueda", "Atacante", 18,
                        PlayerStats(4, 0, 25), "28/08/1998", photo = "ueda",
                    ),
                    player(
                        "japao-4", "Itakura", "Zagueiro", 4,
                        PlayerStats(2, 2, 42), "27/01/1997", photo = "itakura",
                    ),
                    player(
                        "japao-13", "Nakamura", "Atacante", 13,
                        PlayerStats(11, 2, 32), "28/07/2000", photo = "nakamura",
                    ),
                    player(
                        "japao-1", "Suzuki", "Goleiro", 1,
                        PlayerStats(0, 0, 28), "21/08/2002", photo = "suzuki",
                    ),
                ),
                coach = Coach(id = "japao-coach", name = "Hajime Moriyasu"),
            ),
            Team(
                id = "cabo-verde",
                name = "Cabo Verde",
                victories = 0,
                description = "A Seleção de Cabo Verde representa um dos países africanos que " +
                    "mais cresceram no cenário internacional. Conhecida pela organização " +
                    "tática e espírito de equipe, vem conquistando espaço em competições " +
                    "continentais. Sua evolução reflete o desenvolvimento do futebol no país.",
                colors = listOf("#022C5C", "#CF2027", "#FFFFFF"),
                players = listOf(
                    player(
                        "cabo-verde-9", "Benchimol", "Atacante", 9,
                        PlayerStats(1, 1, 7), "29/12/2001", photo = "benchimol",
                    ),
                    player(
                        "cabo-verde-15", "Duarte", "Meia Central", 15,
                        PlayerStats(1, 2, 35), "04/07/1999", photo = "duarte",
                    ),
                    player(
                        "cabo-verde-19", "Livramento", "Atacante", 19,
                        PlayerStats(5, 0, 22), "04/05/2001", photo = "livramento",
                    ),
                    player(
                        "cabo-verde-20", "Ryan Mendes", "Atacante", 20,
                        PlayerStats(22, 7, 102), "08/01/1990", photo = "ryan_mendes",
                    ),
                    player(
                        "cabo-verde-1", "Vozinha", "Goleiro", 1,
                        PlayerStats(0, 0, 90), "03/06/1986", photo = "vozinha",
                    ),
                ),
                coach = Coach(id = "cabo-verde-coach", name = "Bubista"),
            ),
            Team(
                id = "eua",
                name = "EUA",
                victories = 0,
                description = "A Seleção dos Estados Unidos tem se consolidado como uma das " +
                    "principais forças do futebol na América do Norte. Conhecida pela " +
                    "intensidade física e competitividade, investe fortemente no " +
                    "desenvolvimento de jovens talentos. Seu crescimento tem aumentado sua " +
                    "presença em torneios internacionais.",
                colors = listOf("#1F2742", "#B22234", "#FFFFFF"),
                players = listOf(
                    player(
                        "eua-10", "Pulisic", "Atacante", 10,
                        PlayerStats(33, 20, 87), "18/09/1998", photo = "pulisic",
                    ),
                    player(
                        "eua-2", "Dest", "Lateral", 2,
                        PlayerStats(3, 6, 44), "03/11/2000", photo = "dest",
                    ),
                    player(
                        "eua-20", "Balogun", "Atacante", 20,
                        PlayerStats(10, 4, 31), "03/07/2001", photo = "balogun",
                    ),
                    player(
                        "eua-8", "McKennie", "Meia Central", 8,
                        PlayerStats(12, 7, 71), "28/08/1998", photo = "mckennie",
                    ),
                    player(
                        "eua-24", "Freese", "Goleiro", 24,
                        PlayerStats(33, 20, 87), "02/09/1998", photo = "freese",
                    ),
                ),
                coach = Coach(id = "eua-coach", name = "Pochettino"),
            ),
        ),
    )

    private const val PACKAGE_NAME = "com.grupo.albumfigurinhas"

    private fun player(
        id: String,
        name: String,
        position: String,
        number: Int,
        stats: PlayerStats? = null,
        birthDate: String = "",
        photo: String = "",
    ) = Player(
        id = id,
        name = name,
        position = position,
        number = number,
        birthDate = birthDate,
        photo = resourcePhoto("player", photo),
        stats = stats,
    )

    private fun coach(
        id: String,
        name: String,
        stats: CoachStats? = null,
        birthDate: String = "",
        photo: String = "",
    ) = Coach(
        id = id,
        name = name,
        birthDate = birthDate,
        photo = resourcePhoto("coach", photo),
        stats = stats,
    )

    private fun resourcePhoto(kind: String, slug: String): String =
        if (slug.isBlank()) "" else "android.resource://$PACKAGE_NAME/drawable/${kind}_$slug"
}
