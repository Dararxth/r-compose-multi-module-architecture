package com.rxth.multimodule.core.ui.util

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials


@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun Modifier.blurryEffect(
    haze: HazeState,
    blurRadius: Dp = 10.dp,
    color: Color = Color.White.copy(alpha = 0.15f)
): Modifier {
    return this.hazeEffect(
        state = haze,
        style = HazeMaterials.ultraThin().copy(
            blurRadius = blurRadius,
            noiseFactor = 0f,
            tints = listOf(HazeTint(color = color))
        )
    )
}


fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    this.graphicsLayer {
        translationY = scrollState.value.toFloat() / rate
        alpha = 1f - (scrollState.value.toFloat() / (size.height * 1.5f)).coerceIn(0f, 1f)
    }