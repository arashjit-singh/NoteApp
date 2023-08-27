package com.pkg.noteapp.presentation.noteDetail

import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.util.Constants.KEY_NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val repo: NoteRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    init {
        getNoteById()
    }

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun insertOrUpdateNote() {
        viewModelScope.launch {
            val note = Note(
                description = _uiState.value.description,
                title = _uiState.value.title ?: "",
                color = 0,
                id = _uiState.value.noteId,
                dateTime = SystemClock.currentThreadTimeMillis()
            )
            repo.insertOrUpdateNote(note)
        }
    }

    fun getNoteById() {
        val noteId: Int = savedStateHandle[KEY_NOTE_ID] ?: 0
        if (noteId > 0) {
            viewModelScope.launch {
                val note = repo.getNoteById(noteId)
                note?.let {
                    _uiState.value = _uiState.value.copy(
                        title = it.title,
                        description = it.description,
                        noteId = it.id
                    )
                }
            }
        }
    }

    fun updateTitle(text: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(title = text)
        }
    }

    fun updateDescription(text: String) {
        _uiState.value = _uiState.value.copy(description = text)
    }

    data class UiState(
        var message: String? = null,
        var title: String? = null,
        var description: String? = null,
        var noteId: Int? = null,
    )

}