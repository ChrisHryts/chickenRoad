package com.example.chickenRoad.di

import androidx.room.Room
import com.example.chickenRoad.helpers.Constants
import com.example.chickenRoad.room.GameDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java,
            Constants.DB
        ).build()
    }
    single { get<GameDatabase>().gameResultDao() }
}
