package com.grupo.albumfigurinhas.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Competition : Screen("competition")

    data object Team : Screen("team/{teamId}") {
        const val ARG_TEAM_ID = "teamId"
        fun createRoute(teamId: String) = "team/${Uri.encode(teamId)}"
    }

    data object PlayerDetail : Screen("team/{teamId}/player/{playerId}") {
        const val ARG_TEAM_ID = "teamId"
        const val ARG_PLAYER_ID = "playerId"
        fun createRoute(teamId: String, playerId: String) =
            "team/${Uri.encode(teamId)}/player/${Uri.encode(playerId)}"
    }

    data object CoachDetail : Screen("team/{teamId}/coach/{coachId}") {
        const val ARG_TEAM_ID = "teamId"
        const val ARG_COACH_ID = "coachId"
        fun createRoute(teamId: String, coachId: String) =
            "team/${Uri.encode(teamId)}/coach/${Uri.encode(coachId)}"
    }
}
