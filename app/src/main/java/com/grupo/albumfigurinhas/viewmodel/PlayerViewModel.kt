package com.grupo.albumfigurinhas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo.albumfigurinhas.data.model.Player
import com.grupo.albumfigurinhas.data.model.Team
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.state.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlayerDetail(val player: Player, val team: Team)

class PlayerViewModel(
    private val teamId: String,
    private val playerId: String,
    private val repository: CompetitionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<PlayerDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<PlayerDetail>> = _uiState.asStateFlow()

    init {
        loadPlayer()
    }

    fun retry() = loadPlayer()

    private fun loadPlayer() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                coroutineScope {
                    val playerDeferred = async { repository.getPlayer(playerId) }
                    val teamDeferred = async { repository.getTeam(teamId) }
                    UiState.Success(PlayerDetail(playerDeferred.await(), teamDeferred.await()))
                }
            } catch (exception: Exception) {
                UiState.Error(exception.message ?: "Nao foi possivel carregar o jogador")
            }
        }
    }
}
