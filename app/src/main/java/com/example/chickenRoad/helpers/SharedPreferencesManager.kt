package com.example.chickenRoad.helpers

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(Constants.SP_FOLDER, Context.MODE_PRIVATE)

    fun getMusicStatus(): Boolean = sharedPreferences.getBoolean(Constants.SP_BG_MUSIC, true)
    fun setMusicStatus(value: Boolean) = sharedPreferences.edit { putBoolean(Constants.SP_BG_MUSIC, value) }

    fun getSoundStatus(): Boolean = sharedPreferences.getBoolean(Constants.SP_BG_SOUND, true)
    fun setSoundStatus(value: Boolean) = sharedPreferences.edit { putBoolean(Constants.SP_BG_SOUND, value) }
}
