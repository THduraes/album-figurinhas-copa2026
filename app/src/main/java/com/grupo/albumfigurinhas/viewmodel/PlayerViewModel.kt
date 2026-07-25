package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerId: String,
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Player>>(UiState.Loading)
    val uiState: StateFlow<UiState<Player>> = _uiState.asStateFlow()

    init {
        loadPlayer()
    }

    fun retry() = loadPlayer()

    private fun loadPlayer() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getPlayer(playerId))
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar o jogador")
            }
        }
    }
}
