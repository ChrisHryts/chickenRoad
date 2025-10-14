package com.example.chickenRoad.ui.screens.loadingScreen

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.chickenRoad.R
import kotlinx.coroutines.delay
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.chickenRoad.ui.components.Fog
import com.example.chickenRoad.ui.components.Background

@Composable
fun LoadingScreen(onHome: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onHome()
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (background, fog) = createRefs()

        Background(imageRef = R.drawable.background_loading, ref = background)
        Fog(ref = fog, heightPercent = 0.2f)
    }
}
