//@file:OptIn(ExperimentalResourceApi::class)

package DesktopApplication


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
//import org.jetbrains.compose.resources.ExperimentalResourceApi
//import org.jetbrains.compose.resources.painterResource


@Composable
fun AppContent() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
    var loginError by remember { mutableStateOf(false) }

    when (currentScreen) {
        Screen.LOGIN -> LoginScreen(
            onLogin = { enteredUsername, enteredPassword ->
                // Your logic to validate login
                if (isValidUser(enteredUsername, enteredPassword)) {
                    currentScreen = Screen.MAIN_MENU
                } else {
                    loginError = true
                }
            },
            loginError = loginError
        )
        Screen.MAIN_MENU -> MainMenuScreen { selectedScreen -> currentScreen = selectedScreen }
        Screen.SETTINGS -> SettingsScreen { currentScreen = Screen.MAIN_MENU }
        else -> DefaultScreen { currentScreen = Screen.MAIN_MENU } // Fallback screen
    }
}

@Composable
fun DefaultScreen(onBack: () -> Unit) {
    // Default content for unimplemented screens
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Screen not yet implemented (Default)")
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun MainMenuScreen(onScreenSelected: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Screen.values().filter { it != Screen.LOGIN && it != Screen.MAIN_MENU }.forEach { screen ->
            Button(onClick = { onScreenSelected(screen) }) {
                Text(screen.name)
            }
        }
    }
}


@Composable
fun MenuButton(title: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(title)
    }
}

@Composable
fun ContentScreen(screen: Screen, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the ${screen.name} screen", modifier = Modifier.padding(8.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun BackButton(onBack: () -> Unit) {
    Button(onClick = onBack) {
        Text("< Back")
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
            //Image(painterResource("login.png"), null, Modifier.size(20.dp))
            Text("Login", Modifier.padding(start = 10.dp))
        }
    }
}

@Composable
fun openSettingsWindow(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
    ) { BackButton(onBack) }
}

@Composable
fun SettingButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        //Image(painterResource("settings-cog.png"), null, Modifier.size(20.dp))
        Text("Settings", Modifier.padding(start = 10.dp))
    }
}

/*fun writeUserData(id: String, name: String, email: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")

    val user = User(id, name, email)

    myRef.child(id).setValue(user)
}*/

fun isValidUser(username: String, password: String): Boolean {
    // Perform authentication logic (e.g., check against firebase)
    // Return true if the user is valid, false otherwise
    // Replace this with your actual authentication mechanism
    return true;
}