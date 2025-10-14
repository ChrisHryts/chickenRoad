package com.example.chickenRoad.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameResult::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao
}
