package com.example.chickenRoad.ui.screens.ratingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chickenRoad.room.GameResult
import com.example.chickenRoad.room.GameResultDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RatingViewModel(dao: GameResultDao) : ViewModel() {
    val results: StateFlow<List<GameResult>> = dao.getBestResults()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
