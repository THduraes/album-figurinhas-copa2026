package com.grupo.albumfigurinhas.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeCompetitionRepositoryTest {
    private val repository = FakeCompetitionRepository()

    @Test
    fun `returns the selected team`() = runTest {
        val team = repository.getTeam("brasil")

        assertEquals("Brasil", team.name)
        assertEquals(5, team.players.size)
    }

    @Test
    fun `fails when a player does not exist`() = runTest {
        val result = runCatching {
            repository.getPlayer("desconhecido")
        }

        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }
}
