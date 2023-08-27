package com.pkg.noteapp.domain

import com.pkg.noteapp.util.ColorResource

data class Note(
    val id: Int? = null,
    val title: String,
    val description: String?,
    val dateTime: Long,
    val color: ColorResource,
)
