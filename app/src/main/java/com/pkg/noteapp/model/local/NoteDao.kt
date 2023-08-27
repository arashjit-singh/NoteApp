package com.pkg.noteapp.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * from NoteTable")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Upsert
    suspend fun insertNoteInDb(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNoteFromDb(noteEntity: NoteEntity)

    @Query("SELECT * from NoteTable where id = :noteId")
    suspend fun getNoteById(noteId: Int?): NoteEntity?

    @Query("DELETE from NoteTable")
    suspend fun clearNoteDb()
}