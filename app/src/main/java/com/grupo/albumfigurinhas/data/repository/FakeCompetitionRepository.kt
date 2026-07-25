package com.grupo.albumfigurinhas.data.repository

import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.Team

class FakeCompetitionRepository(
    private val competition: Competition = DemoData.competition,
) : CompetitionRepository {
    override suspend fun getCompetition(forceRefresh: Boolean): Competition = competition

    override suspend fun getTeam(teamId: String): Team =
        competition.teams.firstOrNull { it.id == teamId }
            ?: throw NoSuchElementException("Equipe nao encontrada: $teamId")

    override suspend fun getPlayer(playerId: String): Player =
        competition.teams.asSequence()
            .flatMap { it.players.asSequence() }
            .firstOrNull { it.id == playerId }
            ?: throw NoSuchElementException("Jogador nao encontrado: $playerId")

    override suspend fun getCoach(coachId: String): Coach =
        competition.teams.asSequence()
            .map { it.coach }
            .firstOrNull { it.id == coachId }
            ?: throw NoSuchElementException("Treinador nao encontrado: $coachId")
}
