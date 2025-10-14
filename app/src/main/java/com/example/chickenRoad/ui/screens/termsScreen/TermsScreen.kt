package com.example.chickenRoad.ui.screens.termsScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.chickenRoad.R
import com.example.chickenRoad.ui.components.TextContainer

@Composable
fun TermsScreen() {
    TextContainer(
        title = stringResource(R.string.terms_title),
        text = stringResource(R.string.terms_text)
    )
}
