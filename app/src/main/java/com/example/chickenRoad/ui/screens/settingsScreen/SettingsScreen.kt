package com.example.chickenRoad.ui.screens.settingsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Dimension.Companion.wrapContent
import com.example.chickenRoad.R
import com.example.chickenRoad.ui.components.Fog
import org.koin.androidx.compose.koinViewModel
import androidx.constraintlayout.compose.ChainStyle
import com.example.chickenRoad.helpers.LegalType
import com.example.chickenRoad.ui.components.Background
import com.example.chickenRoad.ui.components.IconButton

@Composable
fun SettingsScreen(onLegal: (LegalType) -> Unit, onBack: () -> Unit) {
    val viewModel: SettingsViewModel = koinViewModel()
    val musicEnabled by viewModel.musicEnabled.collectAsStateWithLifecycle()
    val soundEnabled by viewModel.soundEnabled.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.legalType.collect { onLegal(it) }
    }

    SettingsContent(
        musicEnabled = musicEnabled,
        setMusicEnabled = viewModel::setMusicEnabled,
        soundEnabled = soundEnabled,
        setSoundEnabled = viewModel::setSoundEnabled,
        setLegalType = viewModel::setLegalType,
        onClick = viewModel::onClick,
        onBack = onBack
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SettingsContent(
    musicEnabled: Boolean,
    setMusicEnabled: (Boolean) -> Unit,
    soundEnabled: Boolean,
    setSoundEnabled: (Boolean) -> Unit,
    setLegalType: (LegalType) -> Unit,
    onClick: () -> Unit,
    onBack: () -> Unit
) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (background, buttonBack, fog, board, musicLabel, musicToggle, soundLabel, soundToggle, buttonTerms, buttonPrivacy) = createRefs()
        createHorizontalChain(musicLabel, musicToggle, chainStyle = ChainStyle.Spread)
        createHorizontalChain(soundLabel, soundToggle, chainStyle = ChainStyle.Spread)
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

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
            painter = painterResource(R.drawable.board_settings),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(board) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                    width = Dimension.percent(0.95f)
                    height = wrapContent
                }
        )

        Image(
            painter = painterResource(R.drawable.music),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(musicLabel) {
                bottom.linkTo(soundLabel.top, margin = 16.dp)
                start.linkTo(board.start)
            }
        )

        Image(
            painter = painterResource(if (musicEnabled) R.drawable.toggle_on else R.drawable.toggle_off),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(musicToggle) {
                    top.linkTo(musicLabel.top)
                    bottom.linkTo(musicLabel.bottom)
                    end.linkTo(board.end)
                }
                .clickable(role = Role.Button, onClick = {
                    onClick()
                    setMusicEnabled(!musicEnabled)
                })
        )

        Image(
            painter = painterResource(R.drawable.sound),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(soundLabel) {
                bottom.linkTo(buttonPrivacy.top, margin = 12.dp)
                start.linkTo(board.start)
            }
        )

        Image(
            painter = painterResource(if (soundEnabled) R.drawable.toggle_on else R.drawable.toggle_off),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(soundToggle) {
                    top.linkTo(soundLabel.top)
                    bottom.linkTo(soundLabel.bottom)
                    end.linkTo(board.end)
                }
                .clickable(role = Role.Button, onClick = {
                    onClick()
                    setSoundEnabled(!soundEnabled)
                })
        )

        Image(
            painter = painterResource(R.drawable.button_privacy),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(buttonPrivacy) {
                    bottom.linkTo(buttonTerms.top, margin = 6.dp)
                    centerHorizontallyTo(board)
                    width = Dimension.percent(0.55f)
                    height = wrapContent
                }
                .clickable(role = Role.Button, onClick = {
                    onClick()
                    setLegalType(LegalType.PRIVACY)
                })
        )

        Image(
            painter = painterResource(R.drawable.button_terms),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(buttonTerms) {
                    bottom.linkTo(board.bottom, margin = 6.dp)
                    centerHorizontallyTo(board)
                    width = Dimension.percent(0.55f)
                    height = wrapContent
                }
                .clickable(role = Role.Button, onClick = {
                    onClick()
                    setLegalType(LegalType.TERMS)
                })
        )
    }
}
