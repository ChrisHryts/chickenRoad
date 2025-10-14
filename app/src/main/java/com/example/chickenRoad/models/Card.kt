package com.example.chickenRoad.models

data class Card(
    val uid: Int,
    val imageRes: Int,
    val pairKey: Int,
    var isRevealed: Boolean = false,
    var isMatched: Boolean = false
)
