package io.owuor91.mvvmnotesapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import io.owuor91.mvvmnotesapp.R
import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    private val notesViewModel: NotesViewModel by inject()
    private lateinit var splitInstallManager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splitInstallManager = SplitInstallManagerFactory.create(this)

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
            tvProgress.text = "Loading post notes module"
            SplitCompat.install(this)

            val request: SplitInstallRequest =
                SplitInstallRequest.newBuilder().addModule("post_notes").build()
            if (splitInstallManager.installedModules.contains("post_notes")) {
                tvProgress.visibility = View.GONE
                progressBar.visibility = View.GONE
                openPostNotesFeature()
            } else {
                splitInstallManager.startInstall(request)
            }

        }
    }

    fun openPostNotesFeature() {
        startActivity(Intent(this, Class.forName("io.owuor91.post_notes.ui.AddNoteActivity")))
    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener { listener }
    }

    override fun onPause() {
        super.onPause()
        splitInstallManager.unregisterListener { listener }
    }

    protected val listener = SplitInstallStateUpdatedListener { state ->
        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    displayLoadingState("Downloading Post note module", state)
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    displayLoadingState("Already installed", state)
                    openPostNotesFeature()
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    displayLoadingState("Installing Post module", state)
                    Toast.makeText(this, "Installing", Toast.LENGTH_SHORT).show()
                }
                SplitInstallSessionStatus.FAILED -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun displayLoadingState(message: String, state: SplitInstallSessionState) {
        tvProgress.text = message
        progressBar.max = state.totalBytesToDownload().toInt()
        progressBar.progress = state.bytesDownloaded().toInt()
    }
}
