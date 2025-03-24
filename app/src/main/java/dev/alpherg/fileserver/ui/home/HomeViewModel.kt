package dev.alpherg.fileserver.ui.home

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alpherg.fileserver.service.KtorService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class HomeState {
    Running, Initializing, Stopping, Stopped,
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val intent = Intent(context, KtorService::class.java)

    private val _state = MutableStateFlow(HomeState.Stopped)
    val state = _state.asStateFlow()

    fun startServer() = viewModelScope.launch {
        _state.update { HomeState.Initializing }
        delay(500)
        ContextCompat.startForegroundService(context, intent)
        _state.update { HomeState.Running }
    }

    fun stopServer() = viewModelScope.launch {
        _state.update { HomeState.Stopping }
        delay(500)
        context.stopService(intent)
        _state.update { HomeState.Stopped }
    }
}