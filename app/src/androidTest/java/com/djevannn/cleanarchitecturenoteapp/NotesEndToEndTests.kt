package com.djevannn.cleanarchitecturenoteapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.djevannn.cleanarchitecturenoteapp.core.util.TestTags
import com.djevannn.cleanarchitecturenoteapp.di.AppModule
import com.djevannn.cleanarchitecturenoteapp.presentation.MainActivity
import com.djevannn.cleanarchitecturenoteapp.presentation.add_edit_note.AddEditNoteScreen
import com.djevannn.cleanarchitecturenoteapp.presentation.notes.NotesScreen
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
class NotesEndToEndTests {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

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
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val noteColor =
                            it.arguments?.getInt("noteColor")
                                ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = noteColor
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        val title = "test title"
        val editTitle = "2"
        val content = "test content"

        // click on FAB to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // enter text to text fields
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput(title)
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput(content)
        // save
        composeRule.onNodeWithContentDescription("Save")
            .performClick()

        // make sure added note is exists
        composeRule.onNodeWithText(title).assertIsDisplayed()
        // click to edit
        composeRule.onNodeWithText(content).performClick()

        // gather note detail
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals(title)
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals(content)
        // add text editTitle to text field
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput(editTitle)
        // update note
        composeRule.onNodeWithContentDescription("Save")
            .performClick()

        // make sure update applied
        composeRule.onNodeWithText(title + editTitle)
            .assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add")
                .performClick()

            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithContentDescription("Save")
                .performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort")
            .performClick()
        composeRule.onNodeWithContentDescription("Title")
            .performClick()
        composeRule.onNodeWithContentDescription("Last")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")
    }
}














