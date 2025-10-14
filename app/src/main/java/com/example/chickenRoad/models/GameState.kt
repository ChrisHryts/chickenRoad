package com.example.chickenRoad.models

import com.example.chickenRoad.helpers.Constants
import com.example.chickenRoad.helpers.GameStatus

data class GameState(
    val gameId: Int = 0,
    val status: GameStatus = GameStatus.SHOWING,
    val cards: List<Card> = emptyList(),
    val matchedPairs: Int = 0,
    val totalPairs: Int = Constants.TOTAL_PAIRS,
    val totalTimeMs: Long = Constants.TOTAL_TIME_MS,
    val timeLeftMs: Long = Constants.TOTAL_TIME_MS,
    val inputEnabled: Boolean = false
)
