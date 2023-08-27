package com.pkg.noteapp.model

import androidx.room.withTransaction
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.domain.SortBy
import com.pkg.noteapp.domain.SortOrder
import com.pkg.noteapp.model.local.NoteDb
import com.pkg.noteapp.model.local.NoteEntity
import com.pkg.noteapp.model.mapper.toNote
import com.pkg.noteapp.model.mapper.toNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl(private val db: NoteDb) : NoteRepository {

    override suspend fun insertOrUpdateNote(note: Note) {
        db.withTransaction {
            db.provideDao().insertNoteInDb(note.toNoteEntity())
        }
    }

    override suspend fun deleteNote(note: Note) {
        db.withTransaction {
            db.provideDao().deleteNoteFromDb(note.toNoteEntity())
        }
    }

    override fun getAllNotes(sortBy: SortBy, sortOrder: SortOrder): Flow<List<Note>> {
        val sortAsc = if (sortOrder.value.equals(SortOrder.Descending)) 1 else -1
        return flow {
            db.provideDao().getAllNotes().map {
                it.sortedBy {
                    it.title
                }.sortedByDescending { sortAsc }.map {
                    it.toNote()
                }
            }
        }
    }

    override suspend fun getNoteById(id: Int?): Note? {
        val noteEntity: NoteEntity? = db.provideDao().getNoteById(id);
        return noteEntity?.toNote()
    }


}