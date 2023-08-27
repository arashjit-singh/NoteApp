package com.pkg.noteapp.presentation.noteList

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
) : ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var currentNote: Note? = null

    init {
        getAllNotesFromDb()
    }

    private fun getAllNotesFromDb() {
        viewModelScope.launch {
            repo.getAllNotes().collect { list ->
                _uiState.value = _uiState.value.copy(
                    list = list
                )
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            currentNote = note
            repo.deleteNote(note)
            _uiState.value = _uiState.value.copy(
                message = "Note Deleted"
            )
        }
    }

    fun undoNoteDelete() {
        currentNote?.let {
            insertOrUpdateNote(it)
            currentNote = null
            _uiState.value = _uiState.value.copy(
                message = null
            )
        }
    }

    private fun insertOrUpdateNote(note: Note) {
        viewModelScope.launch {
            repo.insertOrUpdateNote(note)
        }
    }

    fun updateSortBy(sortBy: SortBy) {
        _uiState.value = _uiState.value.copy(
            sortBy = sortBy
        )
        sortNoteList()
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        _uiState.value = _uiState.value.copy(
            sortOrder = sortOrder
        )
        sortNoteList()
    }

    private fun sortNoteList() {
        var sortedList: List<Note>
        sortedList = when (_uiState.value.sortBy) {
            SortBy.Title -> _uiState.value.list.sortedBy { it.title }
            SortBy.Date -> _uiState.value.list.sortedBy { it.dateTime }
            SortBy.Color -> _uiState.value.list.sortedBy { it.color.value }
            else -> {
                _uiState.value.list.sortedBy { it.title }
            }
        }

        if (_uiState.value.sortOrder === SortOrder.Descending) {
            sortedList = when (_uiState.value.sortBy) {
                SortBy.Title -> sortedList
                    .sortedByDescending { it.title }

                SortBy.Date -> sortedList
                    .sortedByDescending { it.dateTime }

                SortBy.Color -> sortedList
                    .sortedByDescending { it.color.value }

                else -> {
                    sortedList.sortedByDescending { it.title }
                }
            }
        }

        _uiState.value = _uiState.value.copy(
            list = sortedList
        )
    }

    data class UiState(
        var list: List<Note> = emptyList(),
        var sortBy: SortBy? = null,
        var sortOrder: SortOrder? = null,
        var message: String? = null,
    )
}