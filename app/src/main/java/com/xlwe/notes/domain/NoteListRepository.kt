package com.xlwe.notes.domain

import androidx.lifecycle.LiveData

interface NoteListRepository {
    suspend fun addNoteItem(noteItem: NoteItem)

    suspend fun deleteNoteItem(noteItem: NoteItem)

    suspend fun editNoteItem(noteItem: NoteItem)

    suspend fun getNoteItem(noteItemId: Int): NoteItem

    fun getNoteList(): LiveData<List<NoteItem>>
}