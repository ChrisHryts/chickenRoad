package com.example.chickenRoad.ui.screens.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.chickenRoad.helpers.SharedPreferencesManager
import com.example.chickenRoad.helpers.BackgroundMusicController
import com.example.chickenRoad.helpers.SoundController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val musicController: BackgroundMusicController,
    private val soundController: SoundController
) : ViewModel() {
    private val _musicEnabled = MutableStateFlow(true)
    val musicEnabled = _musicEnabled.asStateFlow()

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled = _soundEnabled.asStateFlow()

    init {
        val isMusicEnabled = sharedPreferencesManager.getMusicStatus()
        _musicEnabled.value = isMusicEnabled
        musicController.setEnabled(isMusicEnabled)

        val isSoundEnabled = sharedPreferencesManager.getSoundStatus()
        _soundEnabled.value = isSoundEnabled
    }

    fun setMusicEnabled(value: Boolean) {
        _musicEnabled.value = value
        sharedPreferencesManager.setMusicStatus(value)
        musicController.setEnabled(value)
    }

    fun setSoundEnabled(value: Boolean) {
        _soundEnabled.value = value
        sharedPreferencesManager.setSoundStatus(value)
    }

    fun onClick() {
        soundController.playClick()
    }
}
