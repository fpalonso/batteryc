package com.blaxsoftware.batteryc.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.blaxsoftware.batteryc.batterymonitor.BatteryMonitorService
import com.blaxsoftware.batteryc.util.PowerManager

class BootingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (PowerManager.getInstance(context).isPowerConnected()) {
            Log.d(TAG, "Power connected. Starting service...")
            context.startService(Intent(context, BatteryMonitorService::class.java))
        } else {
            Log.d(TAG, "Power not connected")
        }
    }

    companion object {

        val TAG = "BootingReceiver"
    }
}
