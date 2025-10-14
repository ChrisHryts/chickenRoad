package com.example.chickenRoad.ui.screens.privacyScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.chickenRoad.R
import com.example.chickenRoad.ui.components.TextContainer

@Composable
fun PrivacyScreen() {
    TextContainer(
        title = stringResource(R.string.privacy_title),
        text = stringResource(R.string.privacy_text)
    )
}
