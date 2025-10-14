package com.example.chickenRoad.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.example.chickenRoad.R

@Composable
fun ConstraintLayoutScope.Background(
    ref: ConstrainedLayoutReference,
    modifier: Modifier = Modifier,
    @DrawableRes imageRef: Int = R.drawable.background_basic
) {
    Image(
        painter = painterResource(imageRef),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.constrainAs(ref) {
            centerTo(parent)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    )
}
