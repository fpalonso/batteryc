package com.blaxsoftware.batteryc.alert

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import com.blaxsoftware.batteryc.R

@Suppress("DEPRECATION") class AlertManager private constructor(val context: Context) {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showBatteryMaxLevelReachedAlert(batteryPercentage: Int) {
        vibrator.vibrate(2000)
        playNotificationSound()
        showNotification(batteryPercentage)
    }

    fun cancelAlert() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun playNotificationSound() {
        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
        ringtone.play()
    }

    private fun showNotification(batteryPercentage: Int) {
        val notificationBuilder = NotificationCompat.Builder(context).setSmallIcon(
                R.drawable.ic_stat_battery_charged).setContentTitle(
                context.getString(R.string.battery_charged)).setContentText(
                context.getString(R.string.battery_level_reached_max, batteryPercentage))
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {

        private var instance: AlertManager? = null
        private val NOTIFICATION_ID = 0

        fun getInstance(context: Context): AlertManager {
            if (instance == null) {
                instance = AlertManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
