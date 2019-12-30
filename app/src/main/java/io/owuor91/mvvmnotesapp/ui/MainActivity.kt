package io.owuor91.mvvmnotesapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.tasks.OnCompleteListener
import com.google.android.play.core.tasks.OnFailureListener
import io.owuor91.mvvmnotesapp.R
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    private val notesViewModel: NotesViewModel by inject()
    //private lateinit var splitInstallManager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesViewModel.fetchNotes()
        observeNotesLiveData()
        fetchPostNotesFeature()
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

    fun fetchPostNotesFeature() {
        fab.setOnClickListener {
            val splitInstallManager = SplitInstallManagerFactory.create(this)
            SplitCompat.install(this)

            val request: SplitInstallRequest =
                SplitInstallRequest.newBuilder().addModule("post_notes").build()
            if (splitInstallManager.installedModules.contains("post_notes")) {
                openPostNotesFeature()
            }

            splitInstallManager.registerListener { listener }
            splitInstallManager.startInstall(request)
                .addOnSuccessListener { listener }
                .addOnFailureListener(OnFailureListener { it ->
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                })
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isComplete && task.isSuccessful) {
                        openPostNotesFeature()
                    }
                })

            splitInstallManager.unregisterListener { listener }


        }
    }

    fun openPostNotesFeature() {
        startActivity(Intent(this, Class.forName("io.owuor91.post_notes.ui.AddNoteActivity")))
    }
}
