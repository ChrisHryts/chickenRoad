package com.example.chickenRoad.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutScope.Fog(
    ref: ConstrainedLayoutReference,
    modifier: Modifier = Modifier,
    heightPercent: Float = 0.4f
) {
    Box(
        modifier = modifier
            .constrainAs(ref) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.percent(heightPercent)
            }
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.00f to Color.Transparent,
                        0.35f to Color.DarkGray.copy(alpha = 0.70f),
                        1.00f to Color(0xFF0B2540).copy(alpha = 0.80f)
                    )
                )
            )
            .blur(16.dp)
    )
}
