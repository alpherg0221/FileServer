package dev.alpherg.fileserver.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alpherg.fileserver.datastore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsRepo @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun setAuthEnabled(value: Boolean) = context.datastore.edit { settings ->
        settings[AUTH_ENABLED] = value
    }

    fun observeAuthEnabled() = context.datastore.data.map { preferences ->
        preferences[AUTH_ENABLED] ?: true
    }

    fun getAuthEnabled() = runBlocking {
        context.datastore.data.first()[AUTH_ENABLED] ?: true
    }


    companion object {
        private val AUTH_ENABLED = booleanPreferencesKey("AuthEnabled")
    }
}