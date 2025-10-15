package com.example.chickenRoad.ui.screens.legalScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chickenRoad.R
import com.example.chickenRoad.helpers.LegalType

@Composable
fun LegalScreen(type: LegalType) {
    val title = when (type) {
        LegalType.PRIVACY -> stringResource(R.string.privacy_title)
        LegalType.TERMS -> stringResource(R.string.terms_title)
    }
    val body = when (type) {
        LegalType.PRIVACY -> stringResource(R.string.privacy_body)
        LegalType.TERMS -> stringResource(R.string.terms_body)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}
