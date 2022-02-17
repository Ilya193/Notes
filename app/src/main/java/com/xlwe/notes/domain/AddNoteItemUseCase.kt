package com.xlwe.notes.domain

import android.util.Log

class AddNoteItemUseCase(
    private val noteListRepository: NoteListRepository
) {
    suspend fun addNoteItem(noteItem: NoteItem) {
        noteListRepository.addNoteItem(noteItem)
        Log.d("TAG", "TEST3")
    }
}