package io.owuor91.mvvmnotesapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.owuor91.mvvmnotesapp.R
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesViewModel.fetchNotes()
        observeNotesLiveData()
    }

    fun observeNotesLiveData() {
        notesViewModel.getNotesResponse().observe(this, Observer { notesList ->
            showNotes(notesList)
        })

        notesViewModel.getNotesError().observe(this, Observer { error ->
            showNotesError(error)
        })
    }

    private fun showNotesError(error: String?) {
        Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
    }


    private fun showNotes(notesList: List<Note>?) {
        Toast.makeText(baseContext, notesList?.size.toString(), Toast.LENGTH_LONG).show()
    }
}
