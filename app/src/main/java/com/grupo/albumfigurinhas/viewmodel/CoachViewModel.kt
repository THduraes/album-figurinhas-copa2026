package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Coach
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CoachDetail(val coach: Coach, val team: Team)

class CoachViewModel(
    private val teamId: String,
    private val coachId: String,
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<CoachDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<CoachDetail>> = _uiState.asStateFlow()

    init {
        loadCoach()
    }

    fun retry() = loadCoach()

    private fun loadCoach() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                coroutineScope {
                    val coachDeferred = async { repository.getCoach(coachId) }
                    val teamDeferred = async { repository.getTeam(teamId) }
                    UiState.Success(CoachDetail(coachDeferred.await(), teamDeferred.await()))
                }
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar o treinador")
            }
        }
    }
}
