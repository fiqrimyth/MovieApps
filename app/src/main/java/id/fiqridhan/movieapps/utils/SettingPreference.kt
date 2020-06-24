package id.fiqridhan.movieapps.utils

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(context:Context) {
    private val mSharedPreferences:SharedPreferences
    init{
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    fun dailysReminder(isActive:Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY_REMINDER, isActive)
        editor.apply()
    }
    fun releasedReminder(isActive:Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(RELEASE_REMINDER, isActive)
        editor.apply()
    }
    fun dailyReminder():Boolean {
        return mSharedPreferences.getBoolean(DAILY_REMINDER, false)
    }
    fun releasedReminder():Boolean {
        return mSharedPreferences.getBoolean(RELEASE_REMINDER, false)
    }

    companion object {
        private val PREFS_NAME = "setting_pref"
        private val DAILY_REMINDER = "isDaily"
        private val RELEASE_REMINDER = "isRelease"
    }
}