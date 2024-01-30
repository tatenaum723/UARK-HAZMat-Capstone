@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "UARK HAZMat Capstone") {
        Button(onClick = { onClick() }) {
            Image(painterResource("settings_cog.svg"), null)
            Text("Setings", Modifier.padding(start = 10.dp))
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}