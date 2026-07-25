package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoachViewModel(
    private val coachId: String,
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Coach>>(UiState.Loading)
    val uiState: StateFlow<UiState<Coach>> = _uiState.asStateFlow()

    init {
        loadCoach()
    }

    fun retry() = loadCoach()

    private fun loadCoach() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getCoach(coachId))
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar o treinador")
            }
        }
    }
}
