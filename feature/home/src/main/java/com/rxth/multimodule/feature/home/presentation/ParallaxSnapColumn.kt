package com.rxth.multimodule.feature.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.rxth.multimodule.core.ui.util.parallaxLayoutModifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParallaxSnapColumn(
    modifier: Modifier = Modifier,
    parallaxRate: Int = 2,
    header: @Composable (scrollState: ScrollState) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()
    var headerHeightPx by remember { mutableFloatStateOf(0f) }

    // 1. Define the Snapping Logic
    val snappingLayout = remember (scrollState, headerHeightPx) {
        object : SnapLayoutInfoProvider {
            override fun calculateSnapOffset(velocity: Float): Float {
                val scroll = scrollState.value.toFloat()
                // Only snap if the header is partially visible
                return if (scroll > 0 && scroll < headerHeightPx) {
                    if (scroll < headerHeightPx / 2) -scroll else headerHeightPx - scroll
                } else 0f
            }
        }
    }
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

    // 2. The Layout Structure
    Column (
        modifier = modifier.verticalScroll(
            state = scrollState,
            flingBehavior = flingBehavior
        )
    ) {
        // Header Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { cords ->
                    // Capture height as soon as it's rendered
                    headerHeightPx = cords.size.height.toFloat()
                }
                .parallaxLayoutModifier(scrollState, parallaxRate)
        ) {
            header(scrollState)
        }

        // Body Content
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}
