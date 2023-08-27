package com.pkg.noteapp.presentation.noteList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.domain.SortBy
import com.pkg.noteapp.domain.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repo: NoteRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    init {
        getAllNotesFromDb()
    }

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var currentNote: Note? = null

    private fun getAllNotesFromDb() {
        viewModelScope.launch {
            repo.getAllNotes(
                sortBy = _uiState.value.sortBy,
                sortOrder = _uiState.value.sortOrder
            ).collect { list ->
                _uiState.emit(UiState(list = list))
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            currentNote = note
            repo.deleteNote(note)
        }
    }

    fun undoNoteDelete() {
        currentNote?.let {
            insertOrUpdateNote(it)
            currentNote = null
        }
    }

    fun insertOrUpdateNote(note: Note) {
        viewModelScope.launch {
            repo.insertOrUpdateNote(note)
        }
    }

    fun updateSortBy(sortBy: SortBy) {
        _uiState.value.sortBy = sortBy
        getAllNotesFromDb()
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        _uiState.value.sortOrder = sortOrder
        getAllNotesFromDb()
    }

    data class UiState(
        var list: List<Note> = emptyList(),
        var sortBy: SortBy = SortBy.Date,
        var sortOrder: SortOrder = SortOrder.Ascending,
        var message: String? = null,
    )
}