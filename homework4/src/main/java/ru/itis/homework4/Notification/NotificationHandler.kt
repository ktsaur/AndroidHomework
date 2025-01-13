package ru.itis.homework4.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import ru.itis.homework4.Activity.MainActivity
import ru.itis.homework4.R

class NotificationHandler(private val appCtx: Context) {
    private val notificationManager =
        appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification(data: NotificationData) {
        createNotificationChannel(data.notificationType)
        val activityIntent = Intent(appCtx, MainActivity::class.java).apply {
            putExtra(appCtx.getString(R.string.open_from_notification), true)
        }
        val pendingIntent = PendingIntent.getActivity(
            appCtx,
            0,
            activityIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(appCtx, appCtx.getString(R.string.channelId))
            .setSmallIcon(R.drawable.ic_cake_24)
            .setContentTitle(data.title)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        data.message.let { message ->
            notificationBuilder.setContentText(message)
        }
        notificationManager.notify(data.id, notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationType: NotificationType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val existingChannel = notificationManager.getNotificationChannel(appCtx.getString(R.string.channelId))
            if (existingChannel != null) {
                notificationManager.deleteNotificationChannel(appCtx.getString(R.string.channelId))
            }

            val importance = when (notificationType) {
                NotificationType.MAX -> NotificationManager.IMPORTANCE_MAX
                NotificationType.LOW -> NotificationManager.IMPORTANCE_LOW
                NotificationType.HIGHT -> NotificationManager.IMPORTANCE_HIGH
                else -> NotificationManager.IMPORTANCE_DEFAULT
            }
            Log.d("Важность = ", "${importance.toString()}")
            val channel = NotificationChannel(
                appCtx.getString(R.string.channelId),
                appCtx.getString(R.string.Default_channel),
                importance
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}