package com.example.chickenRoad.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chickenRoad.helpers.SoundController
import org.koin.compose.koinInject

@Composable
fun IconButton(
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val soundController: SoundController = koinInject()

    Icon(
        painter = painterResource(imageRes),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(onClick = {
                soundController.playClick()
                onClick()
            })
    )
}
