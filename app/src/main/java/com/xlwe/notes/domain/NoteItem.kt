package com.xlwe.notes.domain

data class NoteItem(
    val name: String,
    val text: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

