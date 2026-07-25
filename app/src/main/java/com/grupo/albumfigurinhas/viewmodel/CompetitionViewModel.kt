package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Competition
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CompetitionViewModel(
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Competition>>(UiState.Loading)
    val uiState: StateFlow<UiState<Competition>> = _uiState.asStateFlow()

    init {
        loadCompetition()
    }

    fun retry() = loadCompetition(forceRefresh = true)

    private fun loadCompetition(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getCompetition(forceRefresh))
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar a competicao")
            }
        }
    }
}
