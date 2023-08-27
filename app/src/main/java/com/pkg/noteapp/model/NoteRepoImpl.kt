package com.pkg.noteapp.model

import androidx.room.withTransaction
import com.pkg.noteapp.di.IoDispatcher
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.model.local.NoteDb
import com.pkg.noteapp.model.local.NoteEntity
import com.pkg.noteapp.model.mapper.toNote
import com.pkg.noteapp.model.mapper.toNoteEntity
import com.pkg.noteapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl(
    private val noteDb: NoteDb,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) :
    NoteRepository {

    override suspend fun insertOrUpdateNote(note: Note): Resource<String> {
        try {
            noteDb.withTransaction {
                noteDb.provideDao().insertNoteInDb(note.toNoteEntity())
            }
        } catch (e: Exception) {
            return Resource.Error("Error Occurred while adding note")
        }
        return Resource.Success("Note added..")
    }

    override suspend fun deleteNote(note: Note) {
        noteDb.withTransaction {
            noteDb.provideDao().deleteNoteFromDb(note.toNoteEntity())
        }
    }

    override fun getAllNotes(): Flow<List<Note>> =
        noteDb.provideDao().getAllNotes().map { list ->
            list.map {
                it.toNote()
            }
        }

    override suspend fun getNoteById(id: Int?): Note? {
        val noteEntity: NoteEntity? = noteDb.provideDao().getNoteById(id)
        return noteEntity?.toNote()
    }


}