package com.xlwe.notes.domain

import android.util.Log

class EditNoteItemUseCase(
    private val noteListRepository: NoteListRepository
) {
    suspend fun editShopItem(noteItem: NoteItem) {
        noteListRepository.editNoteItem(noteItem)
    }
}