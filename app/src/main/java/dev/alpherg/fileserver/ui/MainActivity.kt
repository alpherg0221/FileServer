package dev.alpherg.fileserver.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.alpherg.fileserver.utils.AuthResult
import dev.alpherg.fileserver.utils.auth
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (appViewModel.authEnabled.value) {
                    setContent {}
                    auth(this@MainActivity).onSuccess {
                        setContent { FSApp() }
                    }.onFailure { msg ->
                        setContent { AuthResult(msg) }
                    }.onError { msg ->
                        setContent { AuthResult(msg) }
                    }
                } else {
                    setContent { FSApp() }
                }
            }
        }
    }
}