@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.resource

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Login")
    }
}

@Composable
fun SettingButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Image(painterResource("settings_cog.svg"), null)
        Text("Setings", Modifier.padding(start = 10.dp))
    }
}
@Composable
fun MenuButton1(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Menu 1")
    }
}

@Composable
fun MenuButton2(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Menu 2")
    }
}

@Composable
fun MenuButton3(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Menu 3")
    }
}

@Composable
fun MenuButton4(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Menu 4")
    }
}

@Composable
fun MenuButton5(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Menu 5")
    }
}