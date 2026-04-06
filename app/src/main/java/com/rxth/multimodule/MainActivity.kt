package com.rxth.multimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import com.rxth.multimodule.core.ui.theme.AppMaterialTheme
import com.rxth.multimodule.feature.home.HomeScreen
import com.rxth.multimodule.feature.home.SideBarItem
import com.rxth.multimodule.navigation.AppNavKey
import com.rxth.multimodule.navigation.AppNavigation
import com.rxth.multimodule.navigation.rememberNavigator
import kotlin.text.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AppMaterialTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navigator = rememberNavigator()
                    val entryProvider = remember(navigator) { AppNavigation(navigator).get() }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(it)) {
                        HomeScreen(
                            onMenuClick = {
                                when(it) {
                                    SideBarItem.HOME -> {
                                        navigator.navigate(AppNavKey.Home)
                                    }
//                                    SideBarItem.PROFILE -> TODO()
//                                    SideBarItem.SETTINGS -> TODO()
                                    else -> navigator.navigate(AppNavKey.Detail(1))
                                }
                            }
                        ) {
                            NavDisplay(
                                backStack = navigator.backStack,
                                onBack = { navigator.goBack() },
                                entryProvider = entryProvider
                            )
                        }
                    }
                }
            }
        }
    }
}
