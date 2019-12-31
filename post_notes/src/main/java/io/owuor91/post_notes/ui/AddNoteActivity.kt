package io.owuor91.post_notes.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import io.owuor91.mvvmnotesapp.ui.BaseActivity
import io.owuor91.post_notes.R
import io.owuor91.post_notes.di.postNotesModule
import io.owuor91.post_notes.viewmodel.PostNotesViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

private val loadModules by lazy {
    loadKoinModules(postNotesModule)
}

private fun injectFeature() = loadModules

class AddNoteActivity : BaseActivity() {
    private val postNotesViewModel: PostNotesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        injectFeature()
        observePostNoteLiveData()
    }


    fun clickPost(view: View) {
        postNotesViewModel.postNotes(etTitle.text.toString(), etNote.text.toString())
    }

    fun observePostNoteLiveData() {
        postNotesViewModel.getPostNotesResponse().observe(this, Observer { note ->
            Toast.makeText(baseContext, "Success", Toast.LENGTH_LONG).show()
        })

        postNotesViewModel.getPostNotesError().observe(this, Observer { error ->
            Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
        })
    }
}
