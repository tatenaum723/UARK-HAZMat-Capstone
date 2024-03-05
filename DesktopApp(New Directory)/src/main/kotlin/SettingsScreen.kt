package DesktopApplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    // Use Box for overall layout to place elements freely
    Box(modifier = Modifier.fillMaxSize()) {
        // Button at the top left
        Button(onClick = onBack, modifier = Modifier.padding(16.dp)) {
            Text("Back")
        }
        // Centering the text within the Box
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("Settings Screen via SettingsScreen.kt (non-default). This proves that we can build pages independently in their own .kt file")
        }
    }
}
