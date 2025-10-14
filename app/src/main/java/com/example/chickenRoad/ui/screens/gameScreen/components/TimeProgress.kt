package com.example.chickenRoad.ui.screens.gameScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chickenRoad.ui.theme.*

@Composable
fun TimeProgress(
    progress: Float,
    width: Dp,
    modifier: Modifier = Modifier
) {
    val height = 18.dp
    val radius = 4.dp

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(radius))
            .background(Brush.verticalGradient(listOf(BluePrimary, BlueDark)))
            .border(2.dp, BlueLight, RoundedCornerShape(radius))
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(radius - 2.dp))
                .background(Brush.verticalGradient(listOf(TrackInnerDark, TrackInnerDarker)))
                .padding(horizontal = 2.dp, vertical = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(radius))
                    .background(Brush.horizontalGradient(listOf(GreenLight, GreenDark)))
            )
        }
    }
}
