package com.example.chickenRoad.ui.screens.gameScreen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.chickenRoad.R
import com.example.chickenRoad.helpers.GameStatus
import com.example.chickenRoad.models.Card
import com.example.chickenRoad.models.GameState
import com.example.chickenRoad.ui.components.Background
import com.example.chickenRoad.ui.screens.gameScreen.components.GameGrid
import com.example.chickenRoad.ui.screens.gameScreen.components.Header
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    onHome: () -> Unit, onSettings: () -> Unit,
    onGameResult: (Boolean, Long?) -> Unit
) {
    val viewModel: GameViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.status) {
        when (state.status) {
            GameStatus.WON -> {
                val time = state.totalTimeMs - state.timeLeftMs
                viewModel.onSave(time)
                onGameResult(true, time)
            }

            GameStatus.LOST -> onGameResult(false, null)
            else -> Unit
        }
    }

    GameContent(
        state = state,
        onCardClick = viewModel::onCardClick,
        onGoPressed = viewModel::onGoPressed,
        onHome = onHome,
        onSettings = onSettings,
        onClick = viewModel::onClick
    )
}

@Composable
private fun GameContent(
    state: GameState,
    onCardClick: (Card) -> Unit,
    onGoPressed: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    onClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (background, header, grid, buttonGo) = createRefs()
        val topGuide = createGuidelineFromTop(0.08f)
        val gridTopGuide = createGuidelineFromTop(0.25f)
        val bottomGuide = createGuidelineFromBottom(0.04f)

        Background(imageRef = R.drawable.background_game, ref = background)

        Header(
            progress = (state.timeLeftMs.toFloat() / state.totalTimeMs).coerceIn(0f, 1f),
            onHome = onHome,
            onSettings = onSettings,
            modifier = Modifier.constrainAs(header) {
                top.linkTo(topGuide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.percent(0.86f)
                height = Dimension.wrapContent
            }
        )

        GameGrid(
            cards = state.cards,
            inputEnabled = state.inputEnabled,
            onCardClick = { card ->
                onClick()
                onCardClick(card)
            },
            modifier = Modifier.constrainAs(grid) {
                top.linkTo(gridTopGuide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.percent(0.86f)
                height = Dimension.percent(0.62f)
            }
        )

        if (state.status == GameStatus.SHOWING) {
            Image(
                painter = painterResource(R.drawable.button_go),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clickable(onClick = {
                        onClick()
                        onGoPressed()
                    })
                    .constrainAs(buttonGo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomGuide)
                        width = Dimension.percent(0.58f)
                        height = Dimension.wrapContent
                    }
            )
        }
    }
}
