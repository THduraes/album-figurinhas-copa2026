package com.grupo.albumfigurinhas.data.repository

import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.Team

interface CompetitionRepository {
    suspend fun getCompetition(forceRefresh: Boolean = false): Competition

    suspend fun getTeam(teamId: String): Team

    suspend fun getPlayer(playerId: String): Player

    suspend fun getCoach(coachId: String): Coach
}
