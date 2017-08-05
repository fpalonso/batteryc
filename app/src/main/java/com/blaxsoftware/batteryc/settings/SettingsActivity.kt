package com.blaxsoftware.batteryc.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.blaxsoftware.batteryc.R
import com.blaxsoftware.batteryc.batterymonitor.BatteryMonitorService
import com.blaxsoftware.batteryc.util.PowerManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (PowerManager.getInstance(this).isPowerConnected()) {
            Log.d(TAG, "Plugged, starting service...")
            startService(Intent(this, BatteryMonitorService::class.java))
        } else {
            Log.d(TAG, "Unplugged, service won't start")
        }
    }

    companion object {

        val TAG = "SettingsActivity"
    }
}
