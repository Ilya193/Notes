package com.xlwe.notes.di.domain

import com.xlwe.notes.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideAddNoteItemUseCase(noteListRepository: NoteListRepository): AddNoteItemUseCase {
        return AddNoteItemUseCase(noteListRepository)
    }

    @Provides
    fun provideDeleteNoteItemUseCase(noteListRepository: NoteListRepository): DeleteNoteItemUseCase {
        return DeleteNoteItemUseCase(noteListRepository)
    }

    @Provides
    fun provideEditNoteItemUseCase(noteListRepository: NoteListRepository): EditNoteItemUseCase {
        return EditNoteItemUseCase(noteListRepository)
    }

    @Provides
    fun provideGetNoteItemUseCase(noteListRepository: NoteListRepository): GetNoteItemUseCase {
        return GetNoteItemUseCase(noteListRepository)
    }

    @Provides
    fun provideGetNoteListUseCase(noteListRepository: NoteListRepository): GetNoteListUseCase {
        return GetNoteListUseCase(noteListRepository)
    }
}