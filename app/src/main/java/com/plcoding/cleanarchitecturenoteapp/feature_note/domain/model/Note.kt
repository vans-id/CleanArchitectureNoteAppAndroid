package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cleanarchitecturenoteapp.ui.theme.*

@Entity(
    tableName = "notes"
)
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            CreamOrange,
            DeepYellow,
            LimeGreen,
            SoftBlue,
            DeepGreen,
            SoftPink,
            Lavender
        )
    }
}
