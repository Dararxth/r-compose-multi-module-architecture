package com.rxth.multimodule.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rxth.multimodule.feature.home.domain.model.Movie
import com.rxth.multimodule.feature.home.presentation.viewmodel.HomeScreenViewModel
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
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


    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Black) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFFFF955E),
                                    Color(0xFFFF6600),
                                ), startY = 30f
                            ),
                            shape = RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .blur(20.dp)
                        .padding(padding)
                ) {

                }

                val listUpcomingMovies = uiState.upComingMovies?.results.orEmpty()
                ListUpComingMovie(listUpcomingMovies)
            }
        }
    }
}

@Composable
private fun ListUpComingMovie(listUpcomingMovies: List<Movie>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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

                val haze = rememberHazeState()

                val item = listUpcomingMovies[it]
                val thumbnail = "https://image.tmdb.org/t/p/w500" + item.posterPath
                ConstraintLayout(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .wrapContentWidth()
                ) {
                    val (image, blurTop, blur) = createRefs()
                    AsyncImage(
                        model = thumbnail,
                        contentDescription = null,
                        modifier = Modifier
                            .width(200.dp)
                            .hazeSource(haze)
                            .constrainAs(image) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .background(Color.Black.copy(alpha = 0.3f))
                            .hazeEffect(haze) {
                                progressive = HazeProgressive.verticalGradient(
                                    startIntensity = 0f,
                                    endIntensity = 1f
                                )
                            }
                            .constrainAs(blur) {
                                bottom.linkTo(image.bottom)
                                start.linkTo(image.start)
                                end.linkTo(image.end)
                                width = Dimension.fillToConstraints
                            }

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
    color: Color = Color.White.copy(alpha = 0.15f)
): Modifier {
    return this.hazeEffect(
        state = haze,
        style = HazeMaterials.ultraThin().copy(
            blurRadius = 10.dp,
            backgroundColor = color,
            noiseFactor = 0f
        )
    )
}