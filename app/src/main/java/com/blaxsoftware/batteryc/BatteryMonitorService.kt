package com.blaxsoftware.batteryc

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BatteryMonitorService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
