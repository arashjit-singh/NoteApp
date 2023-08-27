package com.pkg.noteapp.di

import android.content.Context
import androidx.room.Room
import com.pkg.noteapp.domain.NoteRepository
import com.pkg.noteapp.model.NoteRepoImpl
import com.pkg.noteapp.model.local.NoteDb
import com.pkg.noteapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): NoteDb {
        return Room.databaseBuilder(
            context = context,
            name = Constants.DB_NAME,
            klass = NoteDb::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepo(
        noteDb: NoteDb,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): NoteRepository {
        return NoteRepoImpl(noteDb = noteDb, dispatcher = dispatcher)
    }
}