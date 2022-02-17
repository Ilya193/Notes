package com.xlwe.notes.data

import com.xlwe.notes.domain.NoteItem

class NoteListMapper {
    fun mapEntityToDbModel(noteItem: NoteItem) = NoteItemDbModel(
        id = noteItem.id,
        name = noteItem.name,
        text = noteItem.text
    )

    fun mapDbModelToEntity(shopItemDbModel: NoteItemDbModel) = NoteItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        text = shopItemDbModel.text
    )

    fun mapListDbModelToListEntity(list: List<NoteItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}