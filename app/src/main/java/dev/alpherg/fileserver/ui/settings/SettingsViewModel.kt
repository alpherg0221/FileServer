package dev.alpherg.fileserver.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alpherg.fileserver.data.SettingsRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: SettingsRepo
) : ViewModel() {
    val authEnabled = repo.observeAuthEnabled().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        repo.getAuthEnabled(),
    )

    fun toggleAuthEnabled() {
        viewModelScope.launch {
            repo.setAuthEnabled(!authEnabled.value)
        }
    }
}