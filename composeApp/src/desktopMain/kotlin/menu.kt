@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShowAllButtons()
{
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginButton {  }
        SettingButton {  }
        MenuButton1 {  }
        MenuButton2 {  }
        MenuButton3 {  }
        MenuButton4 {  }
        MenuButton5 {  }
    }
}
@Composable
fun LoginButton(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Image(painterResource("login.png"), null, Modifier.size(20.dp))
        Text("Login", Modifier.padding(start = 10.dp))

    }

    //Something about switching to the button's page using showContent
}

@Composable
fun SettingButton(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Image(painterResource("settings-cog.png"), null, Modifier.size(20.dp))
        Text("Settings", Modifier.padding(start = 10.dp))
    }

    //Something about switching to the button's page using showContent
}
@Composable
fun MenuButton1(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text("Menu 1")
    }

    //Something about switching to the button's page using showContent
}

@Composable
fun MenuButton2(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text("Menu 2")
    }

    //Something about switching to the button's page using showContent
}

@Composable
fun MenuButton3(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text("Menu 3")
    }

    //Something about switching to the button's page using showContent
}

@Composable
fun MenuButton4(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text("Menu 4")
    }

    //Something about switching to the button's page using showContent
}

@Composable
fun MenuButton5(onClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text("Menu 5")
    }

    //Something about switching to the button's page using showContent
}