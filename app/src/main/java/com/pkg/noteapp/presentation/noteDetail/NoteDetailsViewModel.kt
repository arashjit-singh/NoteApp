package com.pkg.noteapp.presentation.noteDetail

import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.util.ColorResource
import com.pkg.noteapp.util.Constants.KEY_NOTE_ID
import com.pkg.noteapp.util.Resource
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
    private var _uiState: MutableStateFlow<UiState>

    init {
        _uiState = MutableStateFlow(UiState())
        getNoteById()
    }

    val uiState = _uiState.asStateFlow()

    fun insertOrUpdateNote() {
        if (validateFields()) {
            viewModelScope.launch {
                val note = Note(
                    description = _uiState.value.description,
                    title = _uiState.value.title ?: "",
                    color = _uiState.value.color,
                    id = _uiState.value.noteId,
                    dateTime = SystemClock.currentThreadTimeMillis()
                )
                val result = repo.insertOrUpdateNote(note)
                when (result) {
                    is Resource.Error -> _uiState.value = _uiState.value.copy(message = result.msg)
                    is Resource.Success -> _uiState.value =
                        _uiState.value.copy(message = result.data)

                    else -> {
                        Log.i("TAG", "")
                    }

                }
            }
        }
    }

    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(_uiState.value.title)) {
            _uiState.value = _uiState.value.copy(message = "Please enter title")
            return false
        } else if (TextUtils.isEmpty(_uiState.value.description)) {
            _uiState.value = _uiState.value.copy(message = "Please enter description")
            return false
        }

        return true
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
                        noteId = it.id,
                        color = it.color
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

    fun updateBgColor(colorResource: ColorResource) {
        _uiState.value = _uiState.value.copy(color = colorResource)
    }

    fun snackBarShown() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    data class UiState(
        var message: String? = null,
        var title: String? = null,
        var description: String? = null,
        var color: ColorResource = ColorResource.White,
        var noteId: Int? = null,
    )

}