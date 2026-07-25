package com.grupo.albumfigurinhas.data.remote

import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.PlayerStats
import com.grupo.albumfigurinhas.data.model.Team

class FirestoreDataException(message: String) : IllegalStateException(message)

internal object FirestoreDataMapper {
    fun competition(data: Map<String, Any?>, teams: List<Team>) = Competition(
        name = data.requiredString("name", "competicao"),
        edition = data.requiredString("edition", "competicao"),
        trophyImage = data.optionalString("trophyImage"),
        teams = teams,
    )

    fun team(id: String, data: Map<String, Any?>, players: List<Player>): Team {
        val coachData = data["coach"] as? Map<*, *>
            ?: throw FirestoreDataException("Campo coach ausente na equipe $id")

        return Team(
            id = id,
            name = data.requiredString("name", "equipe $id"),
            victories = data.requiredInt("victories", "equipe $id"),
            description = data.optionalString("description"),
            badge = data.optionalString("badge"),
            colors = (data["colors"] as? List<*>)?.filterIsInstance<String>().orEmpty(),
            players = players,
            coach = coachData.toCoach(defaultId = "$id-coach", context = "equipe $id"),
        )
    }

    fun player(id: String, data: Map<String, Any?>): Player {
        val stats = (data["stats"] as? Map<*, *>)?.toPlayerStats("jogador $id")
        return Player(
            id = id,
            name = data.requiredString("name", "jogador $id"),
            position = data.requiredString("position", "jogador $id"),
            number = data.requiredInt("number", "jogador $id"),
            photo = data.optionalString("photo"),
            stats = stats,
        )
    }

    private fun Map<*, *>.toCoach(defaultId: String, context: String) = Coach(
        id = this["id"] as? String ?: defaultId,
        name = requiredString("name", "treinador da $context"),
        photo = optionalString("photo"),
        description = optionalString("description"),
    )

    private fun Map<*, *>.toPlayerStats(context: String) = PlayerStats(
        goals = requiredInt("goals", "estatisticas do $context"),
        assists = requiredInt("assists", "estatisticas do $context"),
        matches = requiredInt("matches", "estatisticas do $context"),
    )

    private fun Map<*, *>.requiredString(field: String, context: String): String =
        (this[field] as? String)?.takeIf(String::isNotBlank)
            ?: throw FirestoreDataException("Campo $field ausente ou vazio em $context")

    private fun Map<*, *>.requiredInt(field: String, context: String): Int =
        (this[field] as? Number)?.toInt()
            ?: throw FirestoreDataException("Campo $field ausente ou invalido em $context")

    private fun Map<*, *>.optionalString(field: String): String = this[field] as? String ?: ""
}
