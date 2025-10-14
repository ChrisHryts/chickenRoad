package com.example.chickenRoad.helpers

import kotlinx.serialization.Serializable

@Serializable
data object LoadingRoute

@Serializable
data object MenuRoute

@Serializable
data object GameRoute

@Serializable
data class GameResultRoute(
    val isWin: Boolean,
    val time: Long? = null
)

@Serializable
data object RatingRoute

@Serializable
data object SettingsRoute

@Serializable
data object PrivacyRoute

@Serializable
data object TermsRoute
