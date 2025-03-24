package dev.alpherg.fileserver.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alpherg.fileserver.data.AppRepo
import dev.alpherg.fileserver.data.SettingsRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(appRepo: AppRepo, settingsRepo: SettingsRepo) : ViewModel() {
    val deviceIP = appRepo.observeDeviceIP().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "127.0.0.1",
    )

    val authEnabled = settingsRepo.observeAuthEnabled().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        settingsRepo.getAuthEnabled(),
    )
}