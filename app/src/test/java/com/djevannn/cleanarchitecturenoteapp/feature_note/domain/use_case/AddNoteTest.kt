package com.djevannn.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.djevannn.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteTest {
    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `Throws error when title is blank`() {
        runBlocking {
            val note = Note("", "test", 1, 1, 1)

            try {
                addNote(note)
            } catch (e: InvalidNoteException) {
                assertThat(e).hasMessageThat()
                    .contains("Please enter a note title")
            }
        }
    }

    @Test
    fun `Throws error when content is blank`() {
        runBlocking {
            val note = Note("test", "", 1, 1, 1)

            try {
                addNote(note)
            } catch (e: InvalidNoteException) {
                assertThat(e).hasMessageThat()
                    .contains("Please enter note content")
            }
        }
    }

    @Test
    fun `Note added when field is valid`() {
        runBlocking {
            val note = Note("test", "test", 1, 1, 1)
            fakeRepository.getNotes().collect { notes ->
                assertThat(notes.contains(note))
            }
        }
    }
}