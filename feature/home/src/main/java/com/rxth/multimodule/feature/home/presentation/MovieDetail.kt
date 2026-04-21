package com.rxth.multimodule.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rxth.multimodule.core.network.RemoteHelper.getImagePath
import com.rxth.multimodule.feature.home.domain.model.Movie


@Composable
fun DetailScreen(
    movie: Movie,
    onBack: () -> Unit,
) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding())
            ) {

                val image = getImagePath(movie.posterPath)

                ParallaxSnapColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    parallaxRate = 2,
                    header = {
                        Image(
                            painter = rememberAsyncImagePainter(image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 500.dp),
                            contentScale = ContentScale.Crop
                        )
                    },
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(900.dp)
                            .background(Color.White)
                    ) {

                    }
                }
            }

            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 15.dp, top = padding.calculateTopPadding())
                    .size(30.dp)

                    .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
                    .clickable(onClick = onBack)
                    .clip(CircleShape)
                    .padding(5.dp)
            )
        }
    }
}
