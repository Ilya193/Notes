package com.xlwe.notes.di.data

import android.app.Application
import android.content.Context
import com.xlwe.notes.data.NoteListDao
import com.xlwe.notes.data.NoteListRepositoryImpl
import com.xlwe.notes.domain.NoteListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideNoteListRepository(noteListDao: NoteListDao): NoteListRepository {
        return NoteListRepositoryImpl(noteListDao)
    }
}