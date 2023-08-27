package com.pkg.noteapp.domain

data class Note(
    val id: Int? = null,
    val title: String,
    val description: String?,
    val dateTime: Long,
    val color: Int,
)
