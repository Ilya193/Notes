package com.xlwe.notes.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.xlwe.notes.R
import com.xlwe.notes.databinding.ActivityNoteItemBinding
import com.xlwe.notes.domain.NoteItem
import com.xlwe.notes.presentation.fragments.NoteItemFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class NoteItemActivity : AppCompatActivity(), NoteItemFragment.OnEditingFinished {
    private val binding: ActivityNoteItemBinding by lazy {
        ActivityNoteItemBinding.inflate(layoutInflater)
    }

    private var screenSettings: String = NoteItemActivity.SETTINGS_UNKNOWN
    private var noteItemId: Int = NoteItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        parseParams()

        if (savedInstanceState == null) {
            startSettingsScreen()
        }
    }

    private fun parseParams() {
        screenSettings = intent.getStringExtra(SCREEN_SETTINGS)
            ?: throw RuntimeException("SCREEN_SETTINGS = null")

        if (screenSettings == SETTINGS_EDIT) {
            noteItemId = intent.getIntExtra(NOTE_ITEM_ID, NoteItem.UNDEFINED_ID)
        }
    }

    private fun startSettingsScreen() {
        val fragment = if (screenSettings == SETTINGS_ADD) {
            NoteItemFragment.newInstanceAdd()
        }
        else {
            NoteItemFragment.newInstanceEdit(noteItemId)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    companion object {
        private const val SCREEN_SETTINGS = "screen_settings"
        private const val NOTE_ITEM_ID = "note_item_id"
        private const val SETTINGS_EDIT = "screen_edit"
        private const val SETTINGS_ADD = "screen_add"
        private const val SETTINGS_UNKNOWN = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, NoteItemActivity::class.java)
            intent.putExtra(SCREEN_SETTINGS, SETTINGS_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, NoteItemActivity::class.java)
            intent.putExtra(SCREEN_SETTINGS, SETTINGS_EDIT)
            intent.putExtra(NOTE_ITEM_ID, shopItemId)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}