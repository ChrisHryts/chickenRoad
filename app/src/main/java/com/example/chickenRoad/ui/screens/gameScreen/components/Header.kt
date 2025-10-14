package com.example.chickenRoad.ui.screens.gameScreen.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.chickenRoad.R
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import com.example.chickenRoad.ui.components.IconButton

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Header(
    progress: Float,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenThird = (LocalConfiguration.current.screenWidthDp / 3f).dp
    val clamped = progress.coerceIn(0f, 1f)
    val animatedProgress = animateFloatAsState(
        targetValue = clamped,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "headerProgress"
    )

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (home, settings, timeLabel, progressContainer) = createRefs()

        IconButton(
            imageRes = R.drawable.ic_home,
            onClick = onHome,
            modifier = Modifier.constrainAs(home) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        IconButton(
            imageRes = R.drawable.ic_settings,
            onClick = onSettings,
            modifier = Modifier.constrainAs(settings) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Image(
            painter = painterResource(R.drawable.time),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(timeLabel) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.value(screenThird)
            }
        )

        TimeProgress(
            progress = animatedProgress.value,
            width = screenThird,
            modifier = Modifier.constrainAs(progressContainer) {
                top.linkTo(timeLabel.bottom, margin = 6.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}
