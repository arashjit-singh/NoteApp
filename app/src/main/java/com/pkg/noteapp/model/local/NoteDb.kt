package com.pkg.noteapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDb : RoomDatabase() {
    abstract fun provideDao(): NoteDao
}