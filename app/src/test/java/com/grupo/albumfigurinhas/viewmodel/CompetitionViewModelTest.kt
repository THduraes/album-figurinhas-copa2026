package com.grupo.albumfigurinhas.viewmodel

import com.grupo.albumfigurinhas.MainDispatcherRule
import com.grupo.albumfigurinhas.data.repository.FakeCompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompetitionViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `loads competition on creation`() = runTest {
        val viewModel = CompetitionViewModel(FakeCompetitionRepository())

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals("Copa do Mundo", (state as UiState.Success).data.name)
    }
}
