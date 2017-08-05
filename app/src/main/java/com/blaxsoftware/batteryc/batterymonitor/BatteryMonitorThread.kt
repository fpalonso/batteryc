package com.blaxsoftware.batteryc.batterymonitor

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.blaxsoftware.batteryc.settings.SettingsContract
import com.blaxsoftware.batteryc.util.PowerManager

internal class BatteryMonitorThread(val context: Context, val interval: Int = 60,
                                    val levelListener: BatteryLevelListener) : Thread() {

    interface BatteryLevelListener {

        fun onBatteryMaxLevelReached(percentage: Int)

        fun onBatteryBelowMaxLevel()
    }

    override fun run() {
        Log.d(TAG, "Monitor thread started")
        while (!isInterrupted) {
            val batteryPercentage = PowerManager.getInstance(context).batteryPercentage().toInt()
            val maxPercentage = PreferenceManager.getDefaultSharedPreferences(context).getString(
                    SettingsContract.KEY_MAX_BATTERY_LEVEL,
                    SettingsContract.DEFAULT_MAX_BATTERY_LEVEL).toInt()
            if (batteryPercentage >= maxPercentage) {
                levelListener.onBatteryMaxLevelReached(batteryPercentage)
            } else {
                levelListener.onBatteryBelowMaxLevel()
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
