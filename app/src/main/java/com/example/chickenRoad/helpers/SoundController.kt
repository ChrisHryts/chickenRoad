package com.example.chickenRoad.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.chickenRoad.R

class SoundController(
    context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    private val soundPool: SoundPool
    private val clickSoundId: Int

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attrs)
            .build()

        clickSoundId = soundPool.load(context, R.raw.click_sound, 1)
    }

    fun playClick() {
        if (sharedPreferencesManager.getSoundStatus()) {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f)
        }
    }
}
