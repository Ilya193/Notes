package com.xlwe.notes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xlwe.notes.domain.AddNoteItemUseCase
import com.xlwe.notes.domain.DeleteNoteItemUseCase
import com.xlwe.notes.domain.GetNoteListUseCase
import com.xlwe.notes.domain.NoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val deleteNoteItemUseCase: DeleteNoteItemUseCase,
) : ViewModel() {
    val noteList = getNoteListUseCase.getNoteList()

    fun deleteNoteItem(noteItem: NoteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteItemUseCase.deleteNoteItem(noteItem)
        }
    }
}