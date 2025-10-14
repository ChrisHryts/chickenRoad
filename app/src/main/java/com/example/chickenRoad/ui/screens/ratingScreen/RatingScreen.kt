package com.example.chickenRoad.ui.screens.ratingScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chickenRoad.R
import com.example.chickenRoad.room.GameResult
import com.example.chickenRoad.ui.components.Background
import com.example.chickenRoad.ui.components.Fog
import com.example.chickenRoad.ui.components.IconButton
import org.koin.androidx.compose.koinViewModel
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingScreen(onBack: () -> Unit) {
    val viewModel: RatingViewModel = koinViewModel()
    val results by viewModel.results.collectAsStateWithLifecycle()

    RatingContent(results = results, onBack = onBack)
}

@SuppressLint("ConfigurationScreenWidthHeight")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun RatingContent(results: List<GameResult>, onBack: () -> Unit) {
    val density = LocalDensity.current
    var boardHeightDp by remember { mutableStateOf(0.dp) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (background, buttonBack, fog, board, list) = createRefs()

        Background(ref = background)
        Fog(ref = fog)

        IconButton(
            imageRes = R.drawable.ic_back,
            onClick = onBack,
            modifier = Modifier.constrainAs(buttonBack) {
                top.linkTo(parent.top, margin = screenHeight * 0.07f)
                start.linkTo(parent.start, margin = screenHeight * 0.04f)
            }
        )

        Image(
            painter = painterResource(R.drawable.board_rating),
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

        Box(
            modifier = Modifier.constrainAs(list) {
                start.linkTo(board.start)
                end.linkTo(board.end)
                top.linkTo(board.top, margin = boardHeightDp / 10)
                bottom.linkTo(board.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.60f)
                    .fillMaxHeight(0.60f)
                    .align(Alignment.Center)
            ) {
                if (results.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_list),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(results) { index, item ->
                            val ordinal = ordinalOf(index + 1)
                            val formatted = Duration.ofMillis(item.timeMs).let {
                                stringResource(
                                    R.string.time_format,
                                    it.toMinutes(),
                                    it.seconds % 60
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ordinal,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Text(
                                    text = formatted,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun ordinalOf(position: Int): String {
    return when (position) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        4 -> "4th"
        5 -> "5th"
        else -> position.toString()
    }
}
