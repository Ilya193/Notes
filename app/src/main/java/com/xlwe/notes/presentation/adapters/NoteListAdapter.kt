package com.xlwe.notes.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xlwe.notes.databinding.NoteItemBinding
import com.xlwe.notes.domain.NoteItem

class NoteListAdapter :
    ListAdapter<NoteItem, NoteListAdapter.NoteItemViewHolder>(NoteItemDiffCallback()) {
    class NoteItemViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onNoteItemClickListener: ((NoteItem) -> Unit)? = null
    var onNoteItemLongClickListener: ((NoteItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        return NoteItemViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val note = getItem(position)

        with(holder) {
            binding.tvName.text = note.name

            binding.root.setOnClickListener {
                onNoteItemClickListener?.invoke(note)
            }

            binding.root.setOnLongClickListener {
                onNoteItemLongClickListener?.invoke(note)
                true
            }
        }
    }
}