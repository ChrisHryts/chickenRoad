package com.example.chickenRoad.ui.screens.gameScreen.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chickenRoad.R
import com.example.chickenRoad.models.Card

@Composable
fun GameGrid(
    cards: List<Card>,
    inputEnabled: Boolean,
    onCardClick: (Card) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize(0.93f)
                .align(Alignment.Center)
        ) {
            items(cards, key = { it.uid }) { card ->
                CardCell(
                    card = card,
                    enabled = inputEnabled,
                    onClick = { onCardClick(card) }
                )
            }
        }
    }
}

@Composable
private fun CardCell(card: Card, enabled: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = enabled) { onClick() }
    ) {
        if (card.isRevealed || card.isMatched) CardImage(imageRes = card.imageRes)
        else CardImage(imageRes = R.drawable.card_default)
    }
}

@Composable
private fun CardImage(@DrawableRes imageRes: Int) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}
