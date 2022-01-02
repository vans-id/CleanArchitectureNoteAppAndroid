package com.djevannn.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Please enter a note title")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Please enter note content")
        }

        repository.insertNote(note)
    }

}