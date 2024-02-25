@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun AppContent() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
    var loginError by remember { mutableStateOf(false) }

    when (currentScreen) {
        Screen.LOGIN -> {
            LoginScreen(
                onLogin = { enteredUsername, enteredPassword ->
                    // Perform authentication logic here (e.g., check against firebase)
                    if (isValidUser(enteredUsername, enteredPassword)) {
                        currentScreen = Screen.MAIN_MENU
                    } else {
                        // Set error state and show an error message
                        loginError = true
                    }
                },
                loginError = loginError
            )
        }
        Screen.MAIN_MENU -> {
            MainMenu(onScreenSelected = { screen -> currentScreen = screen })
        }
        Screen.SETTINGS -> {
            openSettingsWindow(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        Screen.MENU_1 -> {
            openMenu1Window(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        Screen.MENU_2 -> {
            openMenu2Window(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        Screen.MENU_3 -> {
            openMenu3Window(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        Screen.MENU_4 -> {
            openMenu4Window(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        Screen.MENU_5 -> {
            openMenu5Window(onBack = { currentScreen = Screen.MAIN_MENU })
        }
        else -> {
            // Handle unexpected screen value (optional)
            // You can display an error message or log a warning
            Text("Unexpected screen value: $currentScreen")
        }
    }
}

enum class Screen {
    LOGIN,
    MAIN_MENU,
    SETTINGS,
    MENU_1,
    MENU_2,
    MENU_3,
    MENU_4,
    MENU_5
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, loginError: Boolean) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf(loginError) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Username TextField
        TextField(
            value = username,
            onValueChange = {
                username = it
                // Clear error state when the user starts typing
                errorState = false
            },
            label = { Text("Username") },
            isError = loginError, // Apply red border when there's an error
            modifier = Modifier.padding(8.dp)
        )

        // Password TextField
        TextField(
            value = password,
            onValueChange = {
                password = it
                // Clear error state when the user starts typing
                errorState = false
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = loginError, // Apply red border when there's an error
            modifier = Modifier.padding(8.dp)
        )

        // Display error message
        if (loginError) {
            Text(
                "Error: Invalid Username or Password. Please try again.",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Login Button
        Button(onClick = { onLogin(username, password) }) {
            Image(painterResource("login.png"), null, Modifier.size(20.dp))
            Text("Login", Modifier.padding(start = 10.dp))
        }
    }
}

@Composable
fun MainMenu(onScreenSelected: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingButton { onScreenSelected(Screen.SETTINGS) }
        MenuButton1 { onScreenSelected(Screen.MENU_1) }
        MenuButton2 { onScreenSelected(Screen.MENU_2) }
        MenuButton3 { onScreenSelected(Screen.MENU_3) }
        MenuButton4 { onScreenSelected(Screen.MENU_4) }
        MenuButton5 { onScreenSelected(Screen.MENU_5) }
    }
}
fun isValidUser(username: String, password: String): Boolean {
    // Perform authentication logic (e.g., check against firebase)
    // Return true if the user is valid, false otherwise
    // Replace this with your actual authentication mechanism
    return true;
}

/*fun writeUserData(id: String, name: String, email: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")

    val user = User(id, name, email)

    myRef.child(id).setValue(user)
}*/

@Composable
fun openSettingsWindow(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the settings window.")
        BackButton(onBack)
    }
}

@Composable
fun openMenu1Window(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the Menu 1 window.")
        BackButton(onBack)
    }
}

@Composable
fun openMenu2Window(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the Menu 2 window.")
        BackButton(onBack)
    }
}

@Composable
fun openMenu3Window(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the Menu 3 window.")
        BackButton(onBack)
    }
}

@Composable
fun openMenu4Window(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the Menu 4 window.")
        BackButton(onBack)
    }
}

@Composable
fun openMenu5Window(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the Menu 5 window.")
        BackButton(onBack)
    }
}

@Composable
fun SettingButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Image(painterResource("settings-cog.png"), null, Modifier.size(20.dp))
        Text("Settings", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun MenuButton1(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Menu 1")
    }
}

@Composable
fun MenuButton2(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Menu 2")
    }
}

@Composable
fun MenuButton3(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Menu 3")
    }
}

@Composable
fun MenuButton4(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Menu 4")
    }
}

@Composable
fun MenuButton5(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Menu 5")
    }
}

@Composable
fun BackButton(onBack: () -> Unit) {
    Button(onClick = onBack) {
        Text("Back")
    }
}
