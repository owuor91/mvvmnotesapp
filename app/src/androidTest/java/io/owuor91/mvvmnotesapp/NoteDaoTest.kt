package io.owuor91.mvvmnotesapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.owuor91.mvvmnotesapp.database.NoteDao
import io.owuor91.mvvmnotesapp.database.NotesAppDatabase
import io.owuor91.mvvmnotesapp.models.Note
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class NoteDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var notesDao: NoteDao
    private lateinit var database: NotesAppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, NotesAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        notesDao = database.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetWord() = runBlocking {
        val note = Note(1, "abd", "efg")
        notesDao.insert(note)

        val dbWords = notesDao.getAllNotes().waitForValue()
        assertEquals(dbWords[0], note)
    }

    @Test
    @Throws(IOException::class)
    fun getAllWords() = runBlocking {
        val note1 = Note(1, "abd", "efg")
        val note2 = Note(1, "gur", "ywt")
        val note3 = Note(1, "pdx", "657")

        notesDao.insert(listOf(note1, note2, note3))

        val dbWords = notesDao.getAllNotes().waitForValue()

        assert(dbWords.size == 3)
    }

    @Test
    @Throws(IOException::class)
    fun testdelete() = runBlocking {
        val note1 = Note(1, "abd", "efg")
        val note2 = Note(1, "gur", "ywt")
        val note3 = Note(1, "pdx", "657")

        notesDao.insert(listOf(note1, note2, note3))

        notesDao.delete(note1)

        val remainingWords = notesDao.getAllNotes().waitForValue()

        assert(remainingWords.size == 2)
    }
}