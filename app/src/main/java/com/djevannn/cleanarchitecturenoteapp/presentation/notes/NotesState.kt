package com.djevannn.cleanarchitecturenoteapp.presentation.notes

import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)