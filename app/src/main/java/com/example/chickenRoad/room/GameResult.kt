package com.example.chickenRoad.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chickenRoad.helpers.Constants

@Entity(tableName = Constants.DB_TABLE_NAME)
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timeMs: Long,
)
