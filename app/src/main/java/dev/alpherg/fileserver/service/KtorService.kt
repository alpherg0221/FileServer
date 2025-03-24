package dev.alpherg.fileserver.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dev.alpherg.fileserver.R
import dev.alpherg.fileserver.ui.MainActivity


class KtorService : Service() {

    init {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw NotImplementedError()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "FileServerStatus"

        val notificationChannel =
            NotificationChannel(channelId, "FileServerStatus", NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(notificationChannel)

        val notification =
            NotificationCompat.Builder(applicationContext, channelId).setContentTitle("FileServer")
                .setTicker("FileServer").setContentText("FileServer is RUNNING")
                .setSmallIcon(R.drawable.ic_notification).setOngoing(true)
                .setContentIntent(pendingIntent).build()

        return notification
    }
}