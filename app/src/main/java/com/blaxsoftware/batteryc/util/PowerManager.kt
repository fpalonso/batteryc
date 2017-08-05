package com.blaxsoftware.batteryc.util

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class PowerManager private constructor(val context: Context) {

    fun isPowerConnected(): Boolean {
        return batteryStatus().getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) != 0
    }

    fun batteryPercentage(): Float {
        val batteryStatus = batteryStatus()
        return 100 * batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,
                0).toFloat() / batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1).toFloat()

    }

    private fun batteryStatus(): Intent {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        return context.registerReceiver(null, intentFilter)
    }

    companion object {

        private var instance: PowerManager? = null

        fun getInstance(context: Context): PowerManager {
            if (instance == null) {
                instance = PowerManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
