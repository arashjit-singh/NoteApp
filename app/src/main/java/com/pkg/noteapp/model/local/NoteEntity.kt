package com.pkg.noteapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class NoteEntity(
    @PrimaryKey
    val id: Int?,
    val title: String,
    val description: String?,
    val dateTime: Long,
    val color: Long,
)
