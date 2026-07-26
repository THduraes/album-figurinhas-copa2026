package com.grupo.albumfigurinhas

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun splashIsShownWhenAppStarts() {
        composeRule.onNodeWithText("Carregando o album...").assertIsDisplayed()
    }

    @Test
    fun competitionIsShownAfterSplash() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Copa do Mundo").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Copa do Mundo").assertIsDisplayed()
        composeRule.onNodeWithText("Brasil").assertIsDisplayed()
    }
}
