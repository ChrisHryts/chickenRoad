package com.example.chickenRoad.di

import com.example.chickenRoad.ui.screens.gameScreen.GameViewModel
import com.example.chickenRoad.ui.screens.ratingScreen.RatingViewModel
import com.example.chickenRoad.ui.screens.settingsScreen.SettingsViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val ViewModelModule = module {
    viewModel { GameViewModel(get(), get()) }
    viewModel { RatingViewModel(get()) }
    viewModel { SettingsViewModel(get(),get(), get()) }
}
