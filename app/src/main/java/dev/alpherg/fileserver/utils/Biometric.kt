package dev.alpherg.fileserver.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.biometric.BiometricPrompt.AuthenticationResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import dev.alpherg.fileserver.ui.theme.FileServerTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun auth(activity: AppCompatActivity) = suspendCoroutine { c ->
    val executor = ContextCompat.getMainExecutor(activity)
    val biometricManager = BiometricManager.from(activity)

    var failedCount = 0

    val biometricPrompt = BiometricPrompt(activity, executor, object : AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            c.resume(AppResult.Success)
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            failedCount++
            if (failedCount >= 5) c.resume(AppResult.Failure("Authentication Failed"))
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            c.resume(
                AppResult.Error(
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED -> "Authentication Canceled"
                        else -> "Authentication Error"
                    }
                )
            )
        }
    })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authentication Required")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .setConfirmationRequired(false)
        .build()

    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            biometricPrompt.authenticate(promptInfo)
        }

        else -> c.resume(AppResult.Error("Authentication Error"))
    }
}

@Composable
fun AuthResult(msg: String) {
    FileServerTheme {
        Scaffold { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(msg, style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}