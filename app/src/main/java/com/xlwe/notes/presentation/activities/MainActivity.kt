package com.xlwe.notes.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xlwe.notes.R
import com.xlwe.notes.databinding.ActivityMainBinding
import com.xlwe.notes.presentation.adapters.NoteListAdapter
import com.xlwe.notes.presentation.fragments.NoteItemFragment
import com.xlwe.notes.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NoteItemFragment.OnEditingFinished {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var noteItemFragment: FragmentContainerView? = null

    private val viewModel: MainViewModel by viewModels()
    private lateinit var noteListAdapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        noteItemFragment = binding.noteItemContainer

        setupRecyclerView()
        setClickListeners()

        observeViewModel()
    }

    private fun setClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            if (noteItemFragment == null) {
                val intent = NoteItemActivity.newIntentAdd(this)
                startActivity(intent)
            }
            else {
                launchFragment(NoteItemFragment.newInstanceAdd())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.noteList.observe(this) {
            noteListAdapter.submitList(it)
        }
    }

    private fun launchFragment(fragment: NoteItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.noteItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        noteListAdapter = NoteListAdapter()
        binding.rvNoteList.adapter = noteListAdapter

        setClickListenersRecycler()
        swipeElementRecycler()
    }

    private fun swipeElementRecycler() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteListAdapter.currentList[position]
                viewModel.deleteNoteItem(note)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvNoteList)
    }

    private fun setClickListenersRecycler() {
        noteListAdapter.onNoteItemClickListener = {
            if (noteItemFragment == null) {
                val intent = NoteItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            }
            else {
                launchFragment(NoteItemFragment.newInstanceEdit(it.id))
            }
        }

        noteListAdapter.onNoteItemLongClickListener = { note ->
            viewModel.deleteNoteItem(note)
        }
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
}