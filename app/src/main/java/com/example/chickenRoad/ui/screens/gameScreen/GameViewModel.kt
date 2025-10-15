package com.example.chickenRoad.ui.screens.gameScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chickenRoad.helpers.Constants
import com.example.chickenRoad.helpers.GameStatus
import com.example.chickenRoad.helpers.SoundController
import com.example.chickenRoad.models.Card
import com.example.chickenRoad.models.GameState
import com.example.chickenRoad.room.GameResult
import com.example.chickenRoad.room.GameResultDao
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel(
    private val dao: GameResultDao,
    private val soundController: SoundController
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var firstPickIndex: Int? = null
    private var secondPickIndex: Int? = null

    init {
        startNewGame()
    }

    fun onGoPressed() {
        val state = _state.value
        if (state.status != GameStatus.SHOWING) return

        val token = state.gameId
        revealAll(false, onlyUnmatched = true)
        _state.update {
            if (it.gameId != token) it
            else it.copy(status = GameStatus.PLAYING, inputEnabled = true)
        }
        startTimer(token)
    }

    fun onSave(timeMs: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(GameResult(timeMs = timeMs))
        }
    }

    fun onCardClick(card: Card) {
        val state = _state.value
        if (!state.inputEnabled || state.status != GameStatus.PLAYING) return

        val index = state.cards.indexOfFirst { it.uid == card.uid }
        if (index == -1) return

        val currentCard = state.cards[index]
        if (currentCard.isMatched || currentCard.isRevealed) return

        val openedCards = state.cards.toMutableList()
        openedCards[index] = currentCard.copy(isRevealed = true)
        _state.update { it.copy(cards = openedCards) }

        when {
            firstPickIndex == null -> firstPickIndex = index
            secondPickIndex == null -> {
                secondPickIndex = index
                _state.update { it.copy(inputEnabled = false) }
                checkPair(state.gameId)
            }
        }
    }

    private fun startNewGame() {
        val newId = _state.value.gameId + 1

        val chosenImages = Constants.IMAGES.distinct().shuffled().take(Constants.TOTAL_PAIRS)
        val cards = chosenImages.flatMapIndexed { index, imageRes ->
            listOf(
                Card(uid = index * 2, imageRes = imageRes, pairKey = index),
                Card(uid = index * 2 + 1, imageRes = imageRes, pairKey = index)
            )
        }.shuffled(Random(System.currentTimeMillis()))

        firstPickIndex = null
        secondPickIndex = null

        _state.value = GameState(
            gameId = newId,
            status = GameStatus.SHOWING,
            cards = cards.map { it.copy(isRevealed = true) },
            matchedPairs = 0,
            totalPairs = Constants.TOTAL_PAIRS,
            totalTimeMs = Constants.TOTAL_TIME_MS,
            timeLeftMs = Constants.TOTAL_TIME_MS,
            inputEnabled = false
        )
    }

    private fun startTimer(gameIdToken: Int) {
        viewModelScope.launch {
            val tick = 50L

            while (isActive) {
                delay(tick)
                val state = _state.value
                if (state.gameId != gameIdToken || state.status != GameStatus.PLAYING) break

                val timeLeft = (state.timeLeftMs - tick).coerceAtLeast(0)
                if (timeLeft == 0L) {
                    _state.update {
                        if (it.gameId != gameIdToken) it
                        else it.copy(
                            timeLeftMs = 0L,
                            status = GameStatus.LOST,
                            inputEnabled = false
                        )
                    }
                    break
                } else {
                    _state.update {
                        if (it.gameId != gameIdToken) it else it.copy(timeLeftMs = timeLeft)
                    }
                }
            }
        }
    }

    private fun checkPair(gameIdToken: Int) {
        val state = _state.value
        if (state.gameId != gameIdToken) return

        val i1 = firstPickIndex ?: return
        val i2 = secondPickIndex ?: return

        if (i1 == i2) {
            firstPickIndex = null
            secondPickIndex = null
            _state.update { it.copy(inputEnabled = true) }
            return
        }

        val cards = state.cards.toMutableList()
        val a = cards[i1]
        val b = cards[i2]

        if (a.pairKey == b.pairKey) {
            cards[i1] = a.copy(isMatched = true, isRevealed = true)
            cards[i2] = b.copy(isMatched = true, isRevealed = true)
            firstPickIndex = null
            secondPickIndex = null

            val newMatched = state.matchedPairs + 1
            val won = newMatched >= state.totalPairs

            _state.update {
                if (it.gameId != gameIdToken) it
                else it.copy(
                    cards = cards,
                    matchedPairs = newMatched,
                    status = if (won) GameStatus.WON else it.status,
                    inputEnabled = !won
                )
            }
        } else {
            viewModelScope.launch {
                delay(700)
                val state = _state.value
                if (state.gameId != gameIdToken) return@launch

                val hide = state.cards.toMutableList()
                hide.getOrNull(i1)?.let { card -> hide[i1] = card.copy(isRevealed = false) }
                hide.getOrNull(i2)?.let { card -> hide[i2] = card.copy(isRevealed = false) }

                firstPickIndex = null
                secondPickIndex = null
                _state.update { it.copy(cards = hide, inputEnabled = true) }
            }
        }
    }

    private fun revealAll(isRevealed: Boolean, onlyUnmatched: Boolean = false) {
        val updated = _state.value.cards.map { card ->
            if (onlyUnmatched && card.isMatched) card
            else card.copy(isRevealed = isRevealed || card.isMatched)
        }
        _state.update { it.copy(cards = updated) }
    }

    fun onClick() {
        soundController.playClick()
    }
}
