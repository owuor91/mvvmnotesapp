package io.owuor91.mvvmnotesapp

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.mvvmnotesapp.repository.NotesRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import retrofit2.Response

class NotesRepositoryTest : BaseTest() {
    @MockK
    lateinit var notesRepository: NotesRepository

    @Test
    fun `test getNotes`() {

        val noteList: List<Note> = ArrayList<Note>()
        coEvery { notesRepository.getNotes() } returns Response.success(noteList)

        GlobalScope.launch(Dispatchers.IO) {
            assertEquals(notesRepository.getNotes(), noteList)
        }
    }
}