package com.blaxsoftware.batteryc.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.blaxsoftware.batteryc.batterymonitor.BatteryMonitorService

class BootingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BootingReceiver", "onReceive()")
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatusIntent = context?.registerReceiver(null, intentFilter)
        val plugged = batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) ?: 0
        if (plugged != 0) {
            context?.startService(Intent(context, BatteryMonitorService::class.java))
        } else {
            Log.d("BootingReceiver", "Unplugged, service won't start")
        }
    }
}
