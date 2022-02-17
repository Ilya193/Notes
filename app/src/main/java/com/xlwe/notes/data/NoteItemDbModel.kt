package com.xlwe.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_items")
data class NoteItemDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val text: String
)
