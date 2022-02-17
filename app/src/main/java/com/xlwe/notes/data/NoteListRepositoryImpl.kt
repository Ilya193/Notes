package com.xlwe.notes.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.xlwe.notes.domain.NoteItem
import com.xlwe.notes.domain.NoteListRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val noteListDao: NoteListDao
): NoteListRepository {
    //private val noteListDao = AppDatabase.getInstance(application).noteListDao()
    private val mapper = NoteListMapper()

    override suspend fun addNoteItem(noteItem: NoteItem) {
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
        Log.d("TAG", "TEST4")
    }

    override suspend fun deleteNoteItem(noteItem: NoteItem) {
        noteListDao.deleteNoteItem(noteItem.id)
    }

    override suspend fun editNoteItem(noteItem: NoteItem) {
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
    }

    override suspend fun getNoteItem(noteItemId: Int): NoteItem {
        val dbModel = noteListDao.getNoteItem(noteItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getNoteList(): LiveData<List<NoteItem>> = Transformations.map(noteListDao.getShopList()) {
        mapper.mapListDbModelToListEntity(it)
    }
}
