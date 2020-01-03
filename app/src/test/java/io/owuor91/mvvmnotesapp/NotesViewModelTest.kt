package io.owuor91.mvvmnotesapp

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.owuor91.basetest.BaseTest
import io.owuor91.mvvmnotesapp.repository.NotesRepository
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import junit.framework.Assert.assertEquals
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class NotesViewModelTest : BaseTest() {

    @MockK
    lateinit var notesRepository: NotesRepository

    @InjectMockKs
    lateinit var notesViewModel: NotesViewModel

    @Test
    fun `test fetchNotes`() {

        coEvery { notesRepository.getNotes() } returns Response.success(emptyList())

        notesViewModel.fetchNotes()

        val response = notesViewModel.getNotesResponse()


        assertEquals(response.value?.size, 0)
    }

    @Test
    fun `test fetchNotesError`() {
        coEvery { notesRepository.getNotes() } returns
                Response.error(
                    401,
                    ResponseBody.create(MediaType.parse("application/json"), "Not Authorized")
                )

        notesViewModel.fetchNotes()

        val error = notesViewModel.getNotesError()

        assertEquals(error.value?.isEmpty() as Boolean, false)
    }
}