package com.grupo.albumfigurinhas.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Competition : Screen("competition")

    data object Team : Screen("team/{teamId}") {
        const val ARG_TEAM_ID = "teamId"
        fun createRoute(teamId: String) = "team/${Uri.encode(teamId)}"
    }

    data object PlayerDetail : Screen("player/{playerId}") {
        const val ARG_PLAYER_ID = "playerId"
        fun createRoute(playerId: String) = "player/${Uri.encode(playerId)}"
    }

    data object CoachDetail : Screen("coach/{coachId}") {
        const val ARG_COACH_ID = "coachId"
        fun createRoute(coachId: String) = "coach/${Uri.encode(coachId)}"
    }
}
