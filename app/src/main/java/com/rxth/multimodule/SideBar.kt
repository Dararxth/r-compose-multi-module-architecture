package com.rxth.multimodule

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey

@Composable
fun SideBar (
    navItems: List<NavigationItem>,
    selectedTab: NavKey,
    onSelected: (NavKey) -> Unit,
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = Color.Blue.copy(alpha = 0.05f)
    ) {
        navItems.forEach { item ->
            NavigationRailItem(
                selected = selectedTab == item.key,
                onClick = {
                    if (selectedTab != item.key) {
                        onSelected(item.key)
                    } else {
                        /**while (currentNavigator.backStack.size > 1) {
                        currentNavigator.goBack()
                        }**/
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }

}