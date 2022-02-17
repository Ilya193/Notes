package com.xlwe.notes.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xlwe.notes.databinding.FragmentNoteItemBinding
import com.xlwe.notes.domain.NoteItem
import com.xlwe.notes.presentation.viewmodels.NoteItemViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteItemFragment : Fragment(), TextWatcher {
    private var _binding: FragmentNoteItemBinding? = null
    private val binding: FragmentNoteItemBinding
        get() = _binding ?: throw RuntimeException("binding = null")

    private val viewModel: NoteItemViewModel by viewModels()
    private var screenSettings: String = SETTINGS_UNKNOWN
    private var noteItemId: Int = NoteItem.UNDEFINED_ID

    private lateinit var onEditingFinished: OnEditingFinished

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnEditingFinished) {
            onEditingFinished = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startSettingsScreen()
        observeViewModel()
        clickBackspace()

        binding.tvName.addTextChangedListener(this)
        binding.tvText.addTextChangedListener(this)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    private fun observeViewModel() {
        viewModel.finishWork.observe(viewLifecycleOwner) {
            onEditingFinished.onEditingFinished()
        }
    }

    interface OnEditingFinished {
        fun onEditingFinished()
    }

    private fun startSettingsScreen() {
        if (screenSettings == SETTINGS_ADD) {
            startScreenAdd()
        }
        else {
            startScreenEdit()
        }
    }

    private fun startScreenAdd() {
        binding.toolbar.saveBtn.visibility = View.GONE

        binding.toolbar.saveBtn.setOnClickListener {
            val name = binding.tvName.text.toString()
            val text = binding.tvText.text.toString()
            if (name.isNotEmpty())
                viewModel.addNoteItem(NoteItem(name, text))
        }

        settingsEditText()
    }

    private fun settingsEditText() {
        val manager = requireActivity().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        manager.showSoftInput(binding.tvName, InputMethodManager.SHOW_IMPLICIT)

        binding.tvName.requestFocus()
    }

    private fun clickBackspace() {
        binding.toolbar.backspace.setOnClickListener {
            onEditingFinished.onEditingFinished()
        }
    }

    private fun startScreenEdit() {
        var currName: String? = null
        var currText: String? = null

        binding.toolbar.saveBtn.visibility = View.VISIBLE

        viewModel.getNoteItem(noteItemId)
        viewModel.noteItem.observe(viewLifecycleOwner) {
            currName = it.name
            currText = it.text
            binding.tvName.setText(currName)
            binding.tvText.setText(currText)
        }

        binding.toolbar.saveBtn.setOnClickListener {
            val name = binding.tvName.text.toString()
            val text = binding.tvText.text.toString()

            if (currName == name && currText == text)
                onEditingFinished.onEditingFinished()
            else {
                if (name.isNotEmpty())
                    viewModel.editShopItem(name, text)
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        screenSettings = args.getString(SCREEN_SETTINGS) ?: throw RuntimeException("SCREEN_SETTINGS = null")

        if (screenSettings == SETTINGS_EDIT) {
            noteItemId = args.getInt(NOTE_ITEM_ID, NoteItem.UNDEFINED_ID)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val name = binding.tvName.text.toString()

        if (name.isNotEmpty())
            binding.toolbar.saveBtn.visibility = View.VISIBLE
        else
            binding.toolbar.saveBtn.visibility = View.GONE
    }

    companion object {
        private const val SCREEN_SETTINGS = "screen_settings"
        private const val NOTE_ITEM_ID = "note_item_id"
        private const val SETTINGS_EDIT = "screen_edit"
        private const val SETTINGS_ADD = "screen_add"
        private const val SETTINGS_UNKNOWN = ""

        fun newInstanceAdd(): NoteItemFragment {
            return NoteItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_SETTINGS, SETTINGS_ADD)
                }
            }
        }

        fun newInstanceEdit(noteItemId: Int): NoteItemFragment {
            return NoteItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_SETTINGS, SETTINGS_EDIT)
                    putInt(NOTE_ITEM_ID, noteItemId)
                }
            }
        }
    }
}