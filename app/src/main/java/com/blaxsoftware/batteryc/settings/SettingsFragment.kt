package com.blaxsoftware.batteryc.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import com.blaxsoftware.batteryc.R

class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val maxBatteryLevelPreference by lazy {
        findPreference(SettingsContract.KEY_MAX_BATTERY_LEVEL) as ListPreference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
        initPreferences(preferenceScreen.sharedPreferences)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        when (key) {
            SettingsContract.KEY_MAX_BATTERY_LEVEL -> updateMaxBatteryLevelPreference()
        }
    }

    private fun initPreferences(pref: SharedPreferences) {
        initMaxBatteryLevelPreference(pref)
        updateMaxBatteryLevelPreference()
    }

    private fun initMaxBatteryLevelPreference(pref: SharedPreferences) {
        maxBatteryLevelPreference.entries = resources.getStringArray(
                R.array.pref_max_battery_level_entries)
        maxBatteryLevelPreference.entryValues = resources.getStringArray(
                R.array.pref_max_battery_level_entryValues)
        maxBatteryLevelPreference.value = pref.getString(SettingsContract.KEY_MAX_BATTERY_LEVEL,
                SettingsContract.DEFAULT_MAX_BATTERY_LEVEL)
    }

    private fun updateMaxBatteryLevelPreference() {
        maxBatteryLevelPreference.summary = maxBatteryLevelPreference.entry.toString().replace("%",
                "%%", false)
    }
}
