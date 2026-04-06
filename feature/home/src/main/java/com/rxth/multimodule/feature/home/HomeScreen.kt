package com.rxth.multimodule.feature.home

import android.view.RoundedCorner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.ui.NavDisplay
import kotlin.enums.EnumEntries

enum class SideBarItem(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Outlined.Home),
    PROFILE("Profile", Icons.Outlined.Person),
    SETTINGS("Settings", Icons.Outlined.Settings)
}

@Composable
fun HomeScreen(
    onMenuClick: (SideBarItem) -> Unit
    ,content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        var isExpanded by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            SideBarUi(isExpanded, onMenuClick)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.15f))
                    .clickable {
                        isExpanded = !isExpanded
                    }) {

                content()
            }
        }
    }
}

@Composable
private fun SideBarUi(
    isExpanded: Boolean,
    onMenuClick: (SideBarItem) -> Unit
) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val sideBarItems = SideBarItem.entries
    val sideBarWidth = remember(isExpanded) { if (isExpanded) 200.dp else 80.dp }
    val size by animateDpAsState(
        targetValue = if (isExpanded) 60.dp else 40.dp,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .animateContentSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(sideBarWidth)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                modifier = Modifier.size(size),
                imageVector = Icons.Default.Face,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Blue.copy(alpha = 0.5f))
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.15f),
                thickness = 1.dp
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(sideBarItems.size) { index ->
                    val item = sideBarItems[index]
                    Row(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable{
                            onMenuClick(item)
                        }.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.width(35.dp),
                            imageVector = item.icon,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color(0xFF8F8F8F))
                        )
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = fadeIn() + slideInHorizontally(),
                            exit = fadeOut() + slideOutHorizontally()
                        ) {
                            Text(
                                text = item.title,
                                maxLines = 1,
                                modifier = Modifier.basicMarquee(),
                                fontSize = 16.sp,
                                color = Color(0xFF8F8F8F)
                            )
                        }
                    }
                }
            }
        }

        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = Color.Black.copy(alpha = 0.15f),
            thickness = 1.dp
        )
    }
}