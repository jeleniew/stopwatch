package com.example.stop_watch_notify.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    private const val PREFS_NAME = "MyPrefs"
    private const val DEFAULT_RINGTONE_KEY = "default_ringtone"

    private lateinit var sharedPreferences: SharedPreferences

    fun initSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var defaultRingtone: Int
        get() = sharedPreferences.getInt(DEFAULT_RINGTONE_KEY, -1)
        set(value) {
            sharedPreferences.edit().putInt(DEFAULT_RINGTONE_KEY, value).apply()
        }

}