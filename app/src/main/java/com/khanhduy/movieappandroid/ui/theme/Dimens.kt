package com.khanhduy.movieappandroid.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val buttonHeight: Dp = 40.dp,
    val spacerSmall : Dp = 0.dp,
    val spacerMedium : Dp = 0.dp,
    val spacerLarge : Dp = 0.dp
)

val CompactSmallDimens = Dimens(
    buttonHeight = 40.dp,
    spacerSmall = 10.dp,
    spacerMedium = 16.dp,
    spacerLarge = 20.dp
)

val CompactMediumDimens = Dimens(
    buttonHeight = 50.dp,
    spacerSmall = 12.dp,
    spacerMedium = 18.dp,
    spacerLarge = 25.dp
)

val CompactLargeDimens = Dimens(
    buttonHeight = 55.dp,
    spacerSmall = 14.dp,
    spacerMedium = 20.dp,
    spacerLarge = 30.dp
)