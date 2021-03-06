package com.djevannn.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.djevannn.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.djevannn.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.djevannn.cleanarchitecturenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        // in memory database for testing
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

}