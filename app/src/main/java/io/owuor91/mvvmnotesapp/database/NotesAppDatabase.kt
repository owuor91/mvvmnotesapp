package io.owuor91.mvvmnotesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.owuor91.mvvmnotesapp.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesAppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}