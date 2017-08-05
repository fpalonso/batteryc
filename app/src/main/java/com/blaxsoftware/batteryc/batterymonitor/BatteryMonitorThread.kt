package com.blaxsoftware.batteryc.batterymonitor

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.blaxsoftware.batteryc.settings.SettingsContract
import com.blaxsoftware.batteryc.util.PowerManager

internal class BatteryMonitorThread(val context: Context, val interval: Int = 1 /* TODO() */,
                                    val levelListener: BatteryLevelListener) : Thread() {

    interface BatteryLevelListener {

        fun onBatteryMaxLevelReached()
    }

    override fun run() {
        Log.d(TAG, "Monitor thread started")
        while (!isInterrupted) {
            val batteryPercentage = PowerManager.getInstance(context).batteryPercentage()
            val maxPercentage = PreferenceManager.getDefaultSharedPreferences(context).getString(
                    SettingsContract.KEY_MAX_BATTERY_LEVEL,
                    SettingsContract.DEFAULT_MAX_BATTERY_LEVEL)
            Log.d(TAG, "percentage=$batteryPercentage, maxPercentage=$maxPercentage")
            if (batteryPercentage >= maxPercentage.toFloat()) {
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
