package io.owuor91.mvvmnotesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.mvvmnotesapp.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    private val notesMediatorLiveData = MediatorLiveData<List<Note>>()
    private val notesErrorMediatorLiveData = MediatorLiveData<String>()

    fun getNotesResponse(): LiveData<List<Note>> = notesMediatorLiveData

    fun getNotesError(): LiveData<String> = notesErrorMediatorLiveData

    fun fetchNotes() {
        viewModelScope.launch {
            val notesResult = notesRepository.getNotes()
            if (notesResult.isSuccessful) {
                notesMediatorLiveData.postValue(notesResult.body())
            } else {
                notesErrorMediatorLiveData.postValue(notesResult.errorBody().toString())
            }
        }
    }
}