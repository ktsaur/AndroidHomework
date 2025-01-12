package ru.itis.homework4.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.itis.homework4.Activity.MainActivity
import ru.itis.homework4.R

class NotificationHandler(private val appCtx: Context) {
    private val notificationManager =
        appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    fun showNotification(data: NotificationData) {
        val activityIntent = Intent(appCtx, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            appCtx,
            0,
            activityIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val actionPendingIntent = PendingIntent.getActivity(
            appCtx,
            0,
            activityIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(appCtx, "channelId")
            .setSmallIcon(R.drawable.ic_cake_24)
            .setContentTitle(data.title)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_favorite_24, "title", actionPendingIntent
            )
            .setAutoCancel(true)

        data.message.let { message ->
            notificationBuilder.setContentText(message)
        }
        notificationManager.notify(data.id, notificationBuilder.build())
    }

    fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channelId",
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


}