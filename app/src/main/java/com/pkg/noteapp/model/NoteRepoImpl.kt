package com.pkg.noteapp.model

import androidx.room.withTransaction
import com.pkg.noteapp.di.IoDispatcher
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.domain.SortBy
import com.pkg.noteapp.domain.SortOrder
import com.pkg.noteapp.model.local.NoteDb
import com.pkg.noteapp.model.local.NoteEntity
import com.pkg.noteapp.model.mapper.toNote
import com.pkg.noteapp.model.mapper.toNoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl(
    private val noteDb: NoteDb,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) :
    NoteRepository {

    override suspend fun insertOrUpdateNote(note: Note) {
        noteDb.withTransaction {
            noteDb.provideDao().insertNoteInDb(note.toNoteEntity())
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDb.withTransaction {
            noteDb.provideDao().deleteNoteFromDb(note.toNoteEntity())
        }
    }

    override fun getAllNotes(sortBy: SortBy, sortOrder: SortOrder): Flow<List<Note>> =
        noteDb.provideDao().getAllNotes().map { list ->
            list.map {
                it.toNote()
            }
        }

    /*    override fun getAllNotes(sortBy: SortBy, sortOrder: SortOrder): Flow<List<Note>> {
            val sortAsc = if (sortOrder.value.equals(SortOrder.Descending)) 1 else -1
            return flow {

                val notesFromDB = noteDb.provideDao().getAllNotes()

                emit(notesFromDB.map {
                    it.toNote()
                })

                *//*notesFromDB.map {
                it.sortedBy {
                    it.title
                }.sortedByDescending { sortAsc }.map {
                    it.toNote()
                }
            }*//*
        }.flowOn(dispatcher)
    }*/

    override suspend fun getNoteById(id: Int?): Note? {
        val noteEntity: NoteEntity? = noteDb.provideDao().getNoteById(id)
        return noteEntity?.toNote()
    }


}