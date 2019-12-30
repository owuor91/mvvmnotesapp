package io.owuor91.post_notes.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.post_notes.repository.PostNoteRepository
import kotlinx.coroutines.launch

class PostNotesViewModel(private val postNotesRepository: PostNoteRepository) : ViewModel() {

    private val postNotesMediatorLiveData = MediatorLiveData<Note>()
    private val postNotesErrorMediatorLiveData = MediatorLiveData<String>()

    fun getPostNotesResponse() = postNotesMediatorLiveData

    fun getPostNotesError() = postNotesErrorMediatorLiveData

    fun postNotes(title: String, noteText: String) {
        viewModelScope.launch {
            val postNotesResponse = postNotesRepository.postNote(title, noteText)
            if (postNotesResponse.isSuccessful) {
                postNotesMediatorLiveData.postValue(postNotesResponse.body())
            } else {
                postNotesErrorMediatorLiveData.postValue(postNotesResponse.errorBody().toString())
            }
        }
    }
}