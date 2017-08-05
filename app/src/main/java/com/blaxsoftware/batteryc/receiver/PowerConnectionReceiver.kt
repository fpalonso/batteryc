package com.blaxsoftware.batteryc.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.blaxsoftware.batteryc.batterymonitor.BatteryMonitorService

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, BatteryMonitorService::class.java)
        when (intent!!.action) {
            Intent.ACTION_POWER_CONNECTED -> context!!.startService(serviceIntent)
            Intent.ACTION_POWER_DISCONNECTED -> context!!.stopService(serviceIntent)
        }
    }
}
