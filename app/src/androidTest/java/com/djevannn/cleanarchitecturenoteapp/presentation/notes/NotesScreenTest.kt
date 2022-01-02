package com.djevannn.cleanarchitecturenoteapp.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.djevannn.cleanarchitecturenoteapp.core.util.TestTags
import com.djevannn.cleanarchitecturenoteapp.di.AppModule
import com.djevannn.cleanarchitecturenoteapp.presentation.MainActivity
import com.djevannn.cleanarchitecturenoteapp.presentation.util.Screen
import com.djevannn.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@UninstallModules(AppModule::class)
class NotesScreenTest {

    // provide with before func
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // select compose activity to use
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()

            CleanArchitectureNoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        // access context in UI Test -> access resources directory
        // val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION)
            .assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort")
            .performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION)
            .assertIsDisplayed()
    }

    // TODO:
    // Remove Order Section
    // Add Screen
    // Edit Screen
    // Delete
}


















