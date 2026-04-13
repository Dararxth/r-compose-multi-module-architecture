package com.rxth.multimodule.feature.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rxth.multimodule.feature.home.domain.model.Movie
import com.rxth.multimodule.feature.home.presentation.viewmodel.HomeScreenViewModel
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: () -> Unit
) {
    val viewModel: HomeScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var isScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        var last = false

        snapshotFlow { scrollState.value }
            .collect { value ->
                val now = value > 64.dp.value
                if (now != last) {
                    last = now
                    isScrolled = now
                }
            }
    }
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ) { padding ->
            val topBarHeight = padding.calculateTopPadding() + 64.dp
            val rootHazeState = rememberHazeState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeSource(rootHazeState)
                ) {
                    val listUpcomingMovies = uiState.upComingMovies?.results.orEmpty()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(topBarHeight))

                        val shuffledGroups = remember(listUpcomingMovies) {
                            List(4) {
                                listUpcomingMovies.shuffled()
                            }
                        }
                        shuffledGroups.forEach {
                            ListUpComingMovie(listUpcomingMovies = it)
                        }
                    }
                }

                AppBar(isScrolled, rootHazeState, padding)
            }
        }
    }
}

@Composable
private fun AppBar(
    isScrolled: Boolean,
    haze: HazeState,
    padding: PaddingValues
) {

    val animateScale = animateFloatAsState(
        targetValue = if (isScrolled) 1f else 1.05f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    var isSearch by remember { mutableStateOf(false) }

    LaunchedEffect(!isScrolled) {
        if (!isScrolled) isSearch = false
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(64.dp)
            .padding(horizontal = 15.dp)
            .then(
                if (isScrolled) Modifier
                    .hazeEffect(haze) {
                        progressive = HazeProgressive.verticalGradient(
                            startIntensity = 1f,
                            endIntensity = 0f
                        )
                        tints = listOf(HazeTint(Color.Black))

                    }
                else Modifier
            )
            .scale(animateScale.value),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.Transparent)

    ) {
        SharedTransitionLayout {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            start = 15.dp,
                            end = 15.dp
                        )
                        .fillMaxWidth()
                        .animateContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "For Puthik",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                        AnimatedVisibility(
                            visible = !isSearch
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(25.dp)
                                    .sharedElement(
                                        rememberSharedContentState("search"),
                                        animatedVisibilityScope = this
                                    )
                                    .clickable { isSearch = true }
                            )
                        }
                    }
                }
                AnimatedVisibility(visible = isSearch) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .sharedBounds(
                                rememberSharedContentState("search"),
                                animatedVisibilityScope = this
                            )
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(25.dp)
                            )

                            Spacer(Modifier.width(8.dp))

                            var query by remember { mutableStateOf("") }
                            TextField(
                                value = query,
                                onValueChange = { query = it },
                                placeholder = {
                                    Text("Search movies...")
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedPlaceholderColor = Color(0xFFE5E5E5),
                                    unfocusedPlaceholderColor = Color(0xFFE5E5E5),
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Outlined.Cancel,
                                contentDescription = null,
                                tint = Color(0xFFE5E5E5),
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { isSearch = false }
                            )

                        }
                    }
                }
                AnimatedVisibility(
                    visible = isScrolled && !isSearch,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    MovieCategories()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchAnimated() {

    var expanded by remember { mutableStateOf(false) }

    SharedTransitionLayout {
        Box(modifier = Modifier.fillMaxSize()) {

            AnimatedVisibility(
                visible = !expanded
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                        .sharedElement(
                            rememberSharedContentState("search"),
                            animatedVisibilityScope = this
                        )
                        .clickable { expanded = true }
                )
            }

            AnimatedVisibility(
                visible = expanded
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(25.dp)
                            .sharedElement(
                                rememberSharedContentState("search"),
                                animatedVisibilityScope = this@AnimatedVisibility
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Search...", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun MovieCategories() {
    val movieCategories = listOf(
        "All",
        "Action",
        "Comedy",
        "More"
    )
    var selected by remember { mutableStateOf<String?>("All") }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .wrapContentHeight()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        movieCategories.forEach { category ->
            FilterChip(
                selected = selected == category,
                onClick = {
                    selected = category
                },
                label = {
                    Text(
                        text = category,
                        color = if (selected == category) Color.Black else Color.White
                    )
                },
            )
        }
    }
}

@Composable
private fun ListUpComingMovie(modifier: Modifier = Modifier, listUpcomingMovies: List<Movie>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Coming Soon!",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontSize = 20.sp
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            items(
                listUpcomingMovies.size,
                key = { listUpcomingMovies[it].id }
            ) {

                val item = listUpcomingMovies[it]
                val thumbnail = "https://image.tmdb.org/t/p/w500" + item.posterPath
                ConstraintLayout(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .wrapContentWidth()
                ) {
                    AsyncImage(
                        model = thumbnail,
                        contentDescription = null,
                        modifier = Modifier
                            .width(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

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
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }