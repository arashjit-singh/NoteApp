package com.pkg.noteapp.presentation.noteDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
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

    fun insertOrUpdateNote(note: Note) {
        viewModelScope.launch {
            repo.insertOrUpdateNote(note)
        }
    }

    fun getNoteById() {
        val noteId: Int? = savedStateHandle["noteId"]
        noteId?.let {
            viewModelScope.launch {
                val note = repo.getNoteById(noteId)
                note?.let {
                    _uiState.value.note = it
                }
            }
        }
    }

    data class UiState(
        var note: Note? = null,
        var message: String? = null,
    )

}