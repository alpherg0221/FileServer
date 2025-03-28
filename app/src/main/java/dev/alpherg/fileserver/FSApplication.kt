package dev.alpherg.fileserver

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class FSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}