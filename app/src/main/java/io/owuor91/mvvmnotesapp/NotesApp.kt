package io.owuor91.mvvmnotesapp

import android.content.Context
import com.facebook.stetho.Stetho
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import io.owuor91.mvvmnotesapp.di.appModule
import io.owuor91.mvvmnotesapp.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        startKoin {
            androidContext(androidContext = baseContext)
            modules(listOf(appModule, dataModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}