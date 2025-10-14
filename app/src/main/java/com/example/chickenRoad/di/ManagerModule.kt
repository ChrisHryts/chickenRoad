package com.example.chickenRoad.di

import com.example.chickenRoad.helpers.SharedPreferencesManager
import com.example.chickenRoad.helpers.BackgroundMusicController
import com.example.chickenRoad.helpers.SoundController
import org.koin.dsl.module

val ManagerModule = module {
    single { SharedPreferencesManager(get()) }
    single { BackgroundMusicController.get(get()) }
    single { SoundController(get(), get()) }
}
