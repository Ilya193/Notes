package com.xlwe.notes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xlwe.notes.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteItemViewModel @Inject constructor(
    private val getNoteItemUseCase: GetNoteItemUseCase,
    private val addNoteItemUseCase: AddNoteItemUseCase,
    private val editNoteItemUseCase: EditNoteItemUseCase
): ViewModel() {
    private val _noteItem = MutableLiveData<NoteItem>()
    val noteItem: LiveData<NoteItem>
        get() = _noteItem

    private val _finishWork = MutableLiveData<Unit>()
    val finishWork: LiveData<Unit>
        get() = _finishWork

    fun getNoteItem(noteItemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getNoteItemUseCase.getNoteItem(noteItemId)
            _noteItem.value = note
        }
    }

    fun addNoteItem(noteItem: NoteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteItemUseCase.addNoteItem(noteItem)
            finish()
        }
    }

    fun editShopItem(inputTitle: String, inputText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _noteItem.value?.let {
                val note = it.copy(name = inputTitle, text = inputText)
                editNoteItemUseCase.editShopItem(note)
                finish()
            }
        }
    }

    private fun finish() {
        _finishWork.postValue(Unit)
    }
}