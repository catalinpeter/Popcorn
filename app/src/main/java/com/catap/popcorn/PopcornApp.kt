package com.catap.popcorn

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import android.content.SharedPreferences
import android.preference.PreferenceManager

lateinit var Prefs: SharedPreferences

@HiltAndroidApp
class PopcornApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Prefs = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
    }
}