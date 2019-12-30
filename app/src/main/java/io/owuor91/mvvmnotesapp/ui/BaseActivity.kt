package io.owuor91.mvvmnotesapp.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

open class BaseActivity : AppCompatActivity() {

    protected val listener = SplitInstallStateUpdatedListener { state ->
        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show()
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    //Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show()
                    //openPostNotesFeature()
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    Toast.makeText(this, "Installing", Toast.LENGTH_SHORT).show()
                }
                SplitInstallSessionStatus.FAILED -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
