package com.xlwe.notes.domain

import androidx.lifecycle.LiveData

class GetNoteListUseCase(
    private val noteListRepository: NoteListRepository
) {
    fun getNoteList(): LiveData<List<NoteItem>> {
        return noteListRepository.getNoteList()
    }
}