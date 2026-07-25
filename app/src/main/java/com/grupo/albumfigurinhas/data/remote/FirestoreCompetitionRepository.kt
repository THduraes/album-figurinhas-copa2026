package com.grupo.albumfigurinhas.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await

class FirestoreCompetitionRepository(
    private val firestore: FirebaseFirestore,
) : CompetitionRepository {
    private val cacheMutex = Mutex()
    private var cache: Competition? = null

    override suspend fun getCompetition(forceRefresh: Boolean): Competition = cacheMutex.withLock {
        if (!forceRefresh) cache?.let { return@withLock it }
        loadCompetition().also { cache = it }
    }

    override suspend fun getTeam(teamId: String): Team =
        getCompetition().teams.firstOrNull { it.id == teamId }
            ?: throw NoSuchElementException("Equipe nao encontrada: $teamId")

    override suspend fun getPlayer(playerId: String): Player =
        getCompetition().teams.asSequence()
            .flatMap { it.players.asSequence() }
            .firstOrNull { it.id == playerId }
            ?: throw NoSuchElementException("Jogador nao encontrado: $playerId")

    override suspend fun getCoach(coachId: String): Coach =
        getCompetition().teams.asSequence()
            .map { it.coach }
            .firstOrNull { it.id == coachId }
            ?: throw NoSuchElementException("Treinador nao encontrado: $coachId")

    private suspend fun loadCompetition(): Competition {
        val competitionRef = firestore.collection(COMPETITIONS).document(COMPETITION_ID)
        val competitionDocument = competitionRef.get().await()
        if (!competitionDocument.exists()) {
            throw FirestoreDataException(
                "Competicao $COMPETITION_ID nao encontrada. Execute a carga inicial do Firestore.",
            )
        }

        val teamDocuments = competitionRef.collection(TEAMS).get().await().documents
        val teams = coroutineScope {
            teamDocuments.map { document -> async { document.toTeam() } }.awaitAll()
        }.sortedBy(Team::name)

        return FirestoreDataMapper.competition(
            data = competitionDocument.data.orEmpty(),
            teams = teams,
        )
    }

    private suspend fun DocumentSnapshot.toTeam(): Team {
        val players = reference.collection(PLAYERS)
            .get()
            .await()
            .documents
            .map { document ->
                FirestoreDataMapper.player(document.id, document.data.orEmpty())
            }
            .sortedBy(Player::number)

        return FirestoreDataMapper.team(
            id = id,
            data = data.orEmpty(),
            players = players,
        )
    }

    private companion object {
        const val COMPETITIONS = "competitions"
        const val COMPETITION_ID = "copa_mundo_2026"
        const val TEAMS = "teams"
        const val PLAYERS = "players"
    }
}
