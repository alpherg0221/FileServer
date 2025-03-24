package dev.alpherg.fileserver.utils

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun requestNotificationPermission(context: AppCompatActivity) = suspendCoroutine { c ->
    if (ContextCompat.checkSelfPermission(context, POST_NOTIFICATIONS) == PERMISSION_GRANTED) {
        c.resume(AppResult.Success)
    }

    context.registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            c.resume(AppResult.Success)
        } else {
            c.resume(AppResult.Failure(""))
        }
    }.launch(POST_NOTIFICATIONS)
}