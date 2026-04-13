package com.rxth.multimodule

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import com.rxth.multimodule.navigation.AppNavKey


@Composable
fun SideBar (
    navItems: List<NavigationItem>,
    selectedTab: NavKey,
    onSelected: (NavKey) -> Unit,
) {
    val navItems = remember {
        listOf(
            NavigationItem("Dashboard", Icons.Default.Dashboard, AppNavKey.Dashboard),
            NavigationItem(
                "Transactions",
                Icons.Default.History,
                AppNavKey.Transactions
            ),
            NavigationItem("Reports", Icons.Default.Analytics, AppNavKey.Reports),
            NavigationItem("Profile", Icons.Default.Person, AppNavKey.Profile),
        )
    }
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