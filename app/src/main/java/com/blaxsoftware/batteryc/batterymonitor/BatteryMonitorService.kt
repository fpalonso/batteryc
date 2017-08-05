package com.blaxsoftware.batteryc.batterymonitor

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.blaxsoftware.batteryc.alert.AlertManager

class BatteryMonitorService : Service(), BatteryMonitorThread.BatteryLevelListener {

    private var monitorThread: BatteryMonitorThread? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d("BatteryMonitorService", "onCreate()")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        monitorThread = BatteryMonitorThread(context = this, levelListener = this)
        monitorThread!!.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        monitorThread?.interrupt()
        monitorThread = null
    }

    override fun onBatteryMaxLevelReached() {
        AlertManager.getInstance(this).showBatteryMaxLevelReachedAlert()
    }
}
