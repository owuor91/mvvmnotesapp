package io.owuor91.mvvmnotesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.owuor91.mvvmnotesapp.models.Note

@Dao
interface NoteDao : BaseDao<Note> {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Note>>
}