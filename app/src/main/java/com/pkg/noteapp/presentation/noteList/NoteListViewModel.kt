package com.pkg.noteapp.presentation.noteList

import android.util.Log
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
            repo.getAllNotes(
                sortBy = _uiState.value.sortBy, sortOrder = _uiState.value.sortOrder
            ).collect { list ->
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

    fun insertOrUpdateNote(note: Note) {
        viewModelScope.launch {
            repo.insertOrUpdateNote(note)
        }
    }

    fun updateSortBy(sortBy: SortBy) {

        Log.i("list", _uiState.value.list.toString())
        Log.i("sortBy", sortBy.value)

        val updatedList = _uiState.value.list.sortedBy {
            it.dateTime
        }

        Log.i("updatedList", updatedList.toString())

        _uiState.value = _uiState.value.copy(
            list = updatedList,
            sortBy = sortBy
        )
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        Log.i("value", _uiState.value.toString())
        _uiState.value = _uiState.value.copy(
            sortOrder = sortOrder
        )
    }

    data class UiState(
        var list: List<Note> = emptyList(),
        var sortBy: SortBy = SortBy.Date,
        var sortOrder: SortOrder = SortOrder.Ascending,
        var message: String? = null,
    )
}