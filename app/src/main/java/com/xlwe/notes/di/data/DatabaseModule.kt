package com.xlwe.notes.di.data

import android.content.Context
import androidx.room.Room
import com.xlwe.notes.data.AppDatabase
import com.xlwe.notes.data.NoteListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteListDao(appDatabase: AppDatabase): NoteListDao {
        return appDatabase.noteListDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        var INSTANCE: AppDatabase? = null
        synchronized(Any()) {
            INSTANCE?.let {
                return it
            }
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "note_item.db"
            ).build()
            INSTANCE = db
            return db
        }
    }
}