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
        monitorThread = BatteryMonitorThread(context = this, levelListener = this)
        monitorThread!!.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("BatteryMonitorService", "onDestroy()")
        super.onDestroy()
        monitorThread?.interrupt()
        monitorThread = null
        AlertManager.getInstance(this).cancelAlert()
    }

    override fun onBatteryMaxLevelReached(percentage: Int) {
        AlertManager.getInstance(this).showBatteryMaxLevelReachedAlert(percentage)
    }

    override fun onBatteryBelowMaxLevel() {
        AlertManager.getInstance(this).cancelAlert()
    }
}
