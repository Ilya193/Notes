package com.xlwe.notes.domain

class GetNoteItemUseCase(
    private val noteListRepository: NoteListRepository
) {
    suspend fun getNoteItem(noteItem: Int): NoteItem {
        return noteListRepository.getNoteItem(noteItem)
    }
}