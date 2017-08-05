package com.blaxsoftware.batteryc.alert

import android.content.Context
import android.os.Vibrator
import android.util.Log

@Suppress("DEPRECATION") class AlertManager private constructor(context: Context) {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun showBatteryMaxLevelReachedAlert() {
        Log.d("AlertManager", "Vibrating!")
        vibrator.vibrate(2000)
    }

    companion object {

        private var instance: AlertManager? = null

        fun getInstance(context: Context): AlertManager {
            if (instance == null) {
                instance = AlertManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
