package com.kmpstudios.universalAppJc.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImageScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text("Image screen")
    }
}

@Preview(showBackground = true)
@Composable
fun ImageScreenPreview() {
    ImageScreen()
}