package com.pkg.noteapp.domain

import com.pkg.noteapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertOrUpdateNote(note: Note): Resource<String>
    suspend fun deleteNote(note: Note)
    fun getAllNotes(sortBy: SortBy, sortOrder: SortOrder): Flow<List<Note>>
    suspend fun getNoteById(id: Int?): Note?
}

sealed class SortBy(val value: String) {
    object Title : SortBy(value = "1")
    object Date : SortBy(value = "2")
    object Color : SortBy(value = "3")
}

sealed class SortOrder(val value: String) {
    object Ascending : SortOrder(value = "1")
    object Descending : SortOrder(value = "2")
}