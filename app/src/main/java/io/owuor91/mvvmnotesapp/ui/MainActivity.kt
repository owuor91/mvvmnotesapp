package io.owuor91.mvvmnotesapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
            notesViewModel.saveNotes(notesList)
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
        val adapter = NotesRvAdapter(notesList as List<Note>)
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapter
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

    fun fetchNewsFeature(view: View) {
        view.id
        SplitCompat.install(this)

        val request: SplitInstallRequest =
            SplitInstallRequest.newBuilder().addModule("news").build()
        if (splitInstallManager.installedModules.contains("news")) {
            openNewsFeature()
        } else {
            splitInstallManager.startInstall(request)
        }
    }

    fun openPostNotesFeature() {
        startActivity(Intent(this, Class.forName("io.owuor91.post_notes.ui.AddNoteActivity")))
    }

    fun openNewsFeature() {
        startActivity(Intent(this, Class.forName("io.owuor91.news.ui.NewsActivity")))
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
        state.moduleNames().forEach { _ ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    displayLoadingState("Downloading Post note module", state)
                    Log.i("DFD", "Downloading Post note module")
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    Log.i("DFD", "Requires user confirmation")
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    displayLoadingState("Already installed", state)
                    openPostNotesFeature()
                    Log.i("DFD", "Already installed")
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    displayLoadingState("Installing Post module", state)
                    Toast.makeText(this, "Installing", Toast.LENGTH_SHORT).show()
                    Log.i("DFD", "Installing Post module")
                }

                SplitInstallSessionStatus.FAILED -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    Log.i("DFD", "Failed")
                }
                SplitInstallSessionStatus.CANCELED -> {
                    Log.i("DFD", "Canceled")
                }

                SplitInstallSessionStatus.CANCELING -> {
                    Log.i("DFD", "Canceling")
                }

                SplitInstallSessionStatus.DOWNLOADED -> {
                    Log.i("DFD", "Downloaded")
                }

                SplitInstallSessionStatus.PENDING -> {
                    Log.i("DFD", "Pending")
                }

                SplitInstallSessionStatus.UNKNOWN -> {
                    Log.i("DFD", "Unknown")
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
