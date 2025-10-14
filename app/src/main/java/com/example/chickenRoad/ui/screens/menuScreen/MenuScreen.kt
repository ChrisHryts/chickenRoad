package com.example.chickenRoad.ui.screens.menuScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.chickenRoad.R
import com.example.chickenRoad.helpers.SoundController
import com.example.chickenRoad.ui.components.Background
import com.example.chickenRoad.ui.components.Fog
import org.koin.compose.koinInject

@Composable
fun MenuScreen(onGame: () -> Unit, onRating: () -> Unit, onSettings: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (background, fog, logo, buttonPlay, buttonRating, buttonSettings) = createRefs()
        val topGuide = createGuidelineFromTop(0.10f)
        val centerGuide = createGuidelineFromTop(0.30f)
        val betweenButtons = 16.dp

        Background(ref = background)
        Fog(ref = fog)

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(topGuide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.percent(0.56f)
                height = Dimension.wrapContent
            }
        )

        MenuButton(
            imageRes = R.drawable.button_play,
            onClick = onGame,
            modifier = Modifier.constrainAs(buttonPlay) {
                top.linkTo(centerGuide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            }
        )

        MenuButton(
            imageRes = R.drawable.button_rating,
            onClick = onRating,
            modifier = Modifier.constrainAs(buttonRating) {
                top.linkTo(buttonPlay.bottom, margin = betweenButtons)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            }
        )

        MenuButton(
            imageRes = R.drawable.button_settings,
            onClick = onSettings,
            modifier = Modifier.constrainAs(buttonSettings) {
                top.linkTo(buttonRating.bottom, margin = betweenButtons)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            }
        )
    }
}

@Composable
private fun MenuButton(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val soundController: SoundController = koinInject()

    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth(0.65f)
            .clickable(
                role = Role.Button,
                onClick = {
                    soundController.playClick()
                    onClick()
                })
    )
}
