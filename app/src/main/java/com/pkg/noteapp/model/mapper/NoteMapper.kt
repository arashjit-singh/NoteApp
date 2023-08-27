package com.pkg.noteapp.model.mapper

import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.model.local.NoteEntity

fun NoteEntity.toNote(): Note {
    return Note(
        id = id, title = title, description = description, dateTime = dateTime, color = color
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id, title = title, description = description, dateTime = dateTime, color = color
    )
}