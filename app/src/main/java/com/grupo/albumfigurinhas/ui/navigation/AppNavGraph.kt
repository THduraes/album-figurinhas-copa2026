package com.grupo.albumfigurinhas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.ui.screens.CoachDetailScreen
import com.grupo.albumfigurinhas.ui.screens.CompetitionScreen
import com.grupo.albumfigurinhas.ui.screens.PlayerDetailScreen
import com.grupo.albumfigurinhas.ui.screens.SplashScreen
import com.grupo.albumfigurinhas.ui.screens.TeamScreen
import com.grupo.albumfigurinhas.viewmodel.CoachViewModel
import com.grupo.albumfigurinhas.viewmodel.CompetitionViewModel
import com.grupo.albumfigurinhas.viewmodel.PlayerViewModel
import com.grupo.albumfigurinhas.viewmodel.TeamViewModel
import com.grupo.albumfigurinhas.viewmodel.albumViewModelFactory
import kotlinx.coroutines.delay

private const val SPLASH_DURATION_MILLIS = 1200L

@Composable
fun AlbumNavGraph(repository: CompetitionRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            LaunchedEffect(Unit) {
                delay(SPLASH_DURATION_MILLIS)
                navController.navigate(Screen.Competition.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }

            SplashScreen()
        }

        composable(Screen.Competition.route) {
            val factory = remember(repository) {
                albumViewModelFactory { CompetitionViewModel(repository) }
            }
            val viewModel: CompetitionViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            CompetitionScreen(
                uiState = uiState,
                onTeamClick = { navController.navigate(Screen.Team.createRoute(it)) },
                onRetry = viewModel::retry,
            )
        }

        composable(
            route = Screen.Team.route,
            arguments = listOf(navArgument(Screen.Team.ARG_TEAM_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            val teamId = requireNotNull(backStackEntry.arguments?.getString(Screen.Team.ARG_TEAM_ID))
            val factory = remember(repository, teamId) {
                albumViewModelFactory { TeamViewModel(teamId, repository) }
            }
            val viewModel: TeamViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            TeamScreen(
                uiState = uiState,
                onBack = navController::navigateUp,
                onPlayerClick = { navController.navigate(Screen.PlayerDetail.createRoute(teamId, it)) },
                onCoachClick = { navController.navigate(Screen.CoachDetail.createRoute(teamId, it)) },
                onRetry = viewModel::retry,
            )
        }

        composable(
            route = Screen.PlayerDetail.route,
            arguments = listOf(
                navArgument(Screen.PlayerDetail.ARG_TEAM_ID) { type = NavType.StringType },
                navArgument(Screen.PlayerDetail.ARG_PLAYER_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val teamId = requireNotNull(
                backStackEntry.arguments?.getString(Screen.PlayerDetail.ARG_TEAM_ID),
            )
            val playerId = requireNotNull(
                backStackEntry.arguments?.getString(Screen.PlayerDetail.ARG_PLAYER_ID),
            )
            val factory = remember(repository, teamId, playerId) {
                albumViewModelFactory { PlayerViewModel(teamId, playerId, repository) }
            }
            val viewModel: PlayerViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            PlayerDetailScreen(
                uiState = uiState,
                onBack = navController::navigateUp,
                onRetry = viewModel::retry,
            )
        }

        composable(
            route = Screen.CoachDetail.route,
            arguments = listOf(
                navArgument(Screen.CoachDetail.ARG_TEAM_ID) { type = NavType.StringType },
                navArgument(Screen.CoachDetail.ARG_COACH_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val teamId = requireNotNull(
                backStackEntry.arguments?.getString(Screen.CoachDetail.ARG_TEAM_ID),
            )
            val coachId = requireNotNull(
                backStackEntry.arguments?.getString(Screen.CoachDetail.ARG_COACH_ID),
            )
            val factory = remember(repository, teamId, coachId) {
                albumViewModelFactory { CoachViewModel(teamId, coachId, repository) }
            }
            val viewModel: CoachViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            CoachDetailScreen(
                uiState = uiState,
                onBack = navController::navigateUp,
                onRetry = viewModel::retry,
            )
        }
    }
}
