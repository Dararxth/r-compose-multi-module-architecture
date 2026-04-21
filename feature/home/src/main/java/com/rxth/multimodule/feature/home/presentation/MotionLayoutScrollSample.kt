package com.rxth.multimodule.feature.home.presentation

import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.OnSwipe
import androidx.constraintlayout.compose.SwipeDirection
import androidx.constraintlayout.compose.SwipeSide
import androidx.constraintlayout.compose.SwipeTouchUp
import com.rxth.multimodule.feature.home.R


@Composable
fun MotionLayoutScrollSample() {
    val scrollState = rememberLazyListState()
    val maxScrollPx = with(LocalDensity.current) { 200.dp.toPx() }
    var headerScrollPx by remember { mutableFloatStateOf(0f) }

    val progress by remember {
        derivedStateOf {
            (headerScrollPx / maxScrollPx).coerceIn(0f, 1f)
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = -available.y

                // If scrolling up (collapsing header)
                return if (delta > 0 && headerScrollPx < maxScrollPx) {
                    val toConsume = kotlin.comparisons.minOf(delta, maxScrollPx - headerScrollPx)
                    headerScrollPx += toConsume
                    Offset(0f, -toConsume) // Consume scroll so list doesn't move yet
                }
                // If scrolling down (expanding header) - only if list is at top
                else if (delta < 0 && headerScrollPx > 0 && scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset == 0) {
                    val toConsume = kotlin.comparisons.maxOf(delta, -headerScrollPx)
                    headerScrollPx += toConsume
                    Offset(0f, -toConsume)
                } else {
                    Offset.Zero
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                // Handle Auto-Complete snapping when user releases touch
                if (headerScrollPx > 0 && headerScrollPx < maxScrollPx) {
                    val target = if (available.y < 0) {
                        maxScrollPx // Collapse if flinging up
                    } else if (available.y > 0) {
                        0f // Expand if flinging down
                    } else {
                        // Snap to nearest if no significant velocity
                        if (headerScrollPx > maxScrollPx / 2) maxScrollPx else 0f
                    }

                    // Smoothly animate the header the rest of the way, matching the fling velocity
                    animate(
                        initialValue = headerScrollPx,
                        targetValue = target,
                        initialVelocity = -available.y
                    ) { value, _ ->
                        headerScrollPx = value
                    }
                    return available // Consume the fling
                }
                return Velocity.Zero
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        // The MotionLayout acting as a collapsing/morphing header
        MotionLayoutHeader(progress = progress)

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(Modifier.height(10.dp))
            }
            items(100) { index ->
                Text(
                    text = "Scroll Item $index",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .background(Color.White, RoundedCornerShape(15.dp))
                        .padding(10.dp)
                )
            }
            item {
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayoutHeader(progress: Float) {
    val motionScene = MotionScene {
        val box = createRefFor("box")
        val profile = createRefFor("profile")

        val startSet = constraintSet("start") {
            constrain(box) {
                height = 300.dp.asDimension()
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                customColor("bgColor", Color.Gray)
            }

            constrain(profile) {
                centerVerticallyTo(box)
                centerHorizontallyTo(box)
                width = 120.dp.asDimension()
                height = 120.dp.asDimension()
            }
        }

        val endSet = constraintSet("end") {
            constrain(box) {
                height = 100.dp.asDimension()
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
                customColor("bgColor", Color(0xFFFFD700)) // Gold

            }

            constrain(profile) {
                start.linkTo(box.start)
                centerVerticallyTo(box)
                width = 50.dp.asDimension()
                height = 50.dp.asDimension()
            }

        }

        defaultTransition(startSet, endSet) {
            onSwipe = OnSwipe(
                anchor = box,
                side = SwipeSide.Middle,
                direction = SwipeDirection.Up, // Use Up for collapsing
                onTouchUp = SwipeTouchUp.AutoComplete,
                dragThreshold = 0.2f,
                dragScale = 0.2f
            )
        }
    }

    MotionLayout(
        motionScene = motionScene,
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Use properties defined in the MotionScene
        val boxProps = customProperties("box")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("box")
                .background(boxProps.color("bgColor"))
        )
        Image(
            modifier = Modifier
                .layoutId("profile")
                .clip(CircleShape),
            contentDescription = null,
            painter = painterResource(R.drawable.ic_profile),
        )
    }
}
