package com.grupo.albumfigurinhas.data.remote

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class FirestoreDataMapperTest {
    @Test
    fun `maps a complete player document`() {
        val player = FirestoreDataMapper.player(
            id = "brasil-10",
            data = mapOf(
                "name" to "Jogador 10",
                "position" to "Atacante",
                "number" to 10L,
                "photo" to "https://example.com/player.jpg",
                "thumbnail" to "https://example.com/player-card.webp",
                "stats" to mapOf("goals" to 2L, "assists" to 3L, "matches" to 8L),
            ),
        )

        assertEquals("brasil-10", player.id)
        assertEquals(10, player.number)
        assertEquals("https://example.com/player-card.webp", player.thumbnail)
        assertEquals(3, player.stats?.assists)
    }

    @Test
    fun `maps the coach thumbnail from a team document`() {
        val team = FirestoreDataMapper.team(
            id = "brasil",
            data = mapOf(
                "name" to "Brasil",
                "victories" to 5,
                "coach" to mapOf(
                    "name" to "Carlo Ancelotti",
                    "photo" to "https://example.com/ancelotti.webp",
                    "thumbnail" to "https://example.com/ancelotti-card.webp",
                ),
            ),
            players = emptyList(),
        )

        assertEquals("Carlo Ancelotti", team.coach.name)
        assertEquals("https://example.com/ancelotti-card.webp", team.coach.thumbnail)
    }

    @Test
    fun `accepts a player without optional stats`() {
        val player = FirestoreDataMapper.player(
            id = "brasil-1",
            data = mapOf("name" to "Goleiro", "position" to "Goleiro", "number" to 1),
        )

        assertNull(player.stats)
        assertEquals("", player.photo)
    }

    @Test
    fun `rejects a team without coach`() {
        assertThrows(FirestoreDataException::class.java) {
            FirestoreDataMapper.team(
                id = "brasil",
                data = mapOf("name" to "Brasil", "victories" to 5),
                players = emptyList(),
            )
        }
    }

    @Test
    fun `rejects malformed required fields`() {
        assertThrows(FirestoreDataException::class.java) {
            FirestoreDataMapper.player(
                id = "invalido",
                data = mapOf("name" to "", "position" to "Atacante", "number" to "dez"),
            )
        }
    }
}
