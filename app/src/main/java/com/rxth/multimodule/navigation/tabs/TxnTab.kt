package com.rxth.multimodule.navigation.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TxnTab (onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onClick) {
            Text(text = "Txn Tab Another Details")
        }
    }
}