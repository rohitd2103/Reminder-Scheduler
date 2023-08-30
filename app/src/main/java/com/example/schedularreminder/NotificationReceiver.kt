package com.example.schedularreminder

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val reminderText = intent.getStringExtra("reminder_text")
            if (reminderText != null) {
                showNotification(context, reminderText)
            }
        }
    }

    private fun showNotification(context: Context, reminderText: String) {
        val channelId = MainActivity.CHANNEL_ID
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText(reminderText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        fun deleteReminderLayout(reminderLayout: LinearLayout, context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED) {
                    val remindersLayout: LinearLayout = reminderLayout.parent as LinearLayout
                    remindersLayout.removeView(reminderLayout)
                }
            } else {
                val remindersLayout: LinearLayout = reminderLayout.parent as LinearLayout
                remindersLayout.removeView(reminderLayout)
            }
        }
    }
}
