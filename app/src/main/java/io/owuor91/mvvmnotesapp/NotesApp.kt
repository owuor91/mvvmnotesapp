package io.owuor91.mvvmnotesapp

import com.google.android.play.core.splitcompat.SplitCompatApplication
import io.owuor91.mvvmnotesapp.di.appModule
import io.owuor91.mvvmnotesapp.di.dataModule
import org.koin.core.context.startKoin

class NotesApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, dataModule))
        }
    }
}