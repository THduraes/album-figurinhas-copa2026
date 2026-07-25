package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamViewModel(
    private val teamId: String,
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Team>>(UiState.Loading)
    val uiState: StateFlow<UiState<Team>> = _uiState.asStateFlow()

    init {
        loadTeam()
    }

    fun retry() = loadTeam()

    private fun loadTeam() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getTeam(teamId))
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar a equipe")
            }
        }
    }
}
