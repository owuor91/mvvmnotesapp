package io.owuor91.mvvmnotesapp

import android.app.Application
import io.owuor91.mvvmnotesapp.di.appModule
import io.owuor91.mvvmnotesapp.di.dataModule
import org.koin.core.context.startKoin

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, dataModule))
        }
    }
}