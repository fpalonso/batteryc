package com.blaxsoftware.batteryc.batterymonitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.preference.PreferenceManager
import android.util.Log
import com.blaxsoftware.batteryc.settings.SettingsContract

internal class BatteryMonitorThread(val context: Context, val interval: Int = 1 /* TODO() */,
                           val levelListener: BatteryLevelListener) : Thread() {

    interface BatteryLevelListener {

        fun onBatteryMaxLevelReached()
    }

    override fun run() {
        Log.d(TAG, "Monitor thread started")
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        while (!isInterrupted) {
            val batteryStatusIntent = context.registerReceiver(null, intentFilter)
            val level = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val percentage = 100 * level.toFloat() / scale
            val maxPercentage = PreferenceManager.getDefaultSharedPreferences(context).getString(
                    SettingsContract.KEY_MAX_BATTERY_LEVEL,
                    SettingsContract.DEFAULT_MAX_BATTERY_LEVEL)
            if (percentage >= maxPercentage.toFloat()) {
                levelListener.onBatteryMaxLevelReached()
            }
            try {
                Thread.sleep(interval * 1000L)
            } catch(e: Exception) {
                break
            }
        }
        Log.d(TAG, "Monitor thread stopped")
    }

    companion object {

        val TAG = "BatteryMonitorThread"
    }
}
