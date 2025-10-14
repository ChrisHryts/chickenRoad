package com.example.chickenRoad

import android.os.Build
import org.koin.android.ext.android.inject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.chickenRoad.helpers.BackgroundMusicController

class MainActivity : ComponentActivity() {
    private val sharedPreferencesManager: com.example.chickenRoad.helpers.SharedPreferencesManager by inject()
    private val musicController: BackgroundMusicController by inject()
    private var musicEnabled: Boolean = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        musicEnabled = sharedPreferencesManager.getMusicStatus()
        musicController.setEnabled(musicEnabled)
        musicController.start(R.raw.bg_sound)

        setContent {
            AppNavigation()
        }
    }

    override fun onStart() {
        super.onStart()
        if (musicEnabled) musicController.resume()
    }

    override fun onStop() {
        super.onStop()
        musicController.pause()
    }
}
