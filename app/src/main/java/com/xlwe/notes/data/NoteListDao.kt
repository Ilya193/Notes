package com.xlwe.notes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteListDao {
    @Query("SELECT * FROM note_items")
    fun getShopList(): LiveData<List<NoteItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteItem(noteItemDbModel: NoteItemDbModel)

    @Query("DELETE FROM note_items WHERE id=:noteItemId")
    suspend fun deleteNoteItem(noteItemId: Int)

    @Query("SELECT * FROM note_items WHERE id=:noteItemId LIMIT 1")
    suspend fun getNoteItem(noteItemId: Int): NoteItemDbModel
}