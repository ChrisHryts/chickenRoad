package com.example.chickenRoad.ui.screens.gameResultScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.constraintlayout.compose.Dimension
import com.example.chickenRoad.R
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ChainStyle
import com.example.chickenRoad.ui.components.Background
import com.example.chickenRoad.ui.components.IconButton
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameResultScreen(
    isWin: Boolean,
    time: Long? = null,
    onHome: () -> Unit,
    onGame: () -> Unit
) {
    val density = LocalDensity.current
    var buttonHeightDp by remember { mutableStateOf(0.dp) }
    var boardHeightDp by remember { mutableStateOf(0.dp) }
    val relativeSize = boardHeightDp / 5.7f
    val formattedTime = Duration.ofMillis(time ?: 0L).let {
        stringResource(R.string.time_format, it.toMinutes(), it.seconds % 60)
    }

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (background, board, text, buttonHome, buttonRestart, gap, buttonNext) = createRefs()
        createHorizontalChain(buttonHome, gap, buttonRestart, chainStyle = ChainStyle.Packed)

        Background(imageRef = R.drawable.background_game, ref = background)
        if (isWin) Background(imageRef = R.drawable.background_win, ref = background)

        Image(
            painter = painterResource(if (isWin) R.drawable.board_win else R.drawable.board_loss),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    boardHeightDp = with(density) { coordinates.size.height.toDp() }
                }
                .constrainAs(board) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                    width = Dimension.percent(0.95f)
                    height = Dimension.wrapContent
                }
        )

        Text(
            text = if (isWin) stringResource(R.string.your_time, formattedTime)
            else stringResource(R.string.try_again),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(buttonHome.top, margin = 8.dp)
                centerHorizontallyTo(board)
            }
        )

        IconButton(
            imageRes = R.drawable.ic_home,
            size = relativeSize,
            onClick = onHome,
            modifier = Modifier.constrainAs(buttonHome) {
                bottom.linkTo(board.bottom, margin = relativeSize)
            }
        )

        IconButton(
            imageRes = R.drawable.ic_restart,
            size = relativeSize,
            onClick = onGame,
            modifier = Modifier.constrainAs(buttonRestart) {
                bottom.linkTo(board.bottom, margin = relativeSize)
            }
        )

        Spacer(
            modifier = Modifier.constrainAs(gap) {
                top.linkTo(buttonHome.top)
                bottom.linkTo(buttonHome.bottom)
                width = Dimension.value(boardHeightDp / 10)
                height = Dimension.value(0.dp)
            }
        )

        Image(
            painter = painterResource(
                if (isWin) R.drawable.button_next_win else R.drawable.button_next_loss
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(2.5f)
                .onGloballyPositioned { coordinates ->
                    buttonHeightDp = with(density) { coordinates.size.height.toDp() }
                }
                .constrainAs(buttonNext) {
                    top.linkTo(board.bottom)
                    centerHorizontallyTo(board)
                }
                .offset(y = -buttonHeightDp / 2)
                .clickable(enabled = isWin, onClick = onGame)
        )
    }
}
