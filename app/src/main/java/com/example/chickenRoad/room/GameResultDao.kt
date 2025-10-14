package com.example.chickenRoad.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chickenRoad.helpers.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM ${Constants.DB_TABLE_NAME} ORDER BY timeMs ASC LIMIT 5")
    fun getBestResults(): Flow<List<GameResult>>
}
