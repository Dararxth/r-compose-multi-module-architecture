package com.rxth.multimodule.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigateToDetail: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen (Feature Module)", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        
        // 3 "frames" (represented as cards/buttons)
        (1..3).forEach { id ->
            Card(
                modifier = Modifier.padding(8.dp),
                onClick = { onNavigateToDetail(id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Frame $id")
                    Text(text = "Click to see details", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
