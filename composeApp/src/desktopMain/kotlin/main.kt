@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.ExperimentalResourceApi

/*fun initializeFirebase() {
    val serviceAccount = FileInputStream("path/to/google-services.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://console.firebase.google.com/u/0/project/haz-mat-7ad0f/database/haz-mat-7ad0f-default-rtdb/data")
        .build()

    FirebaseApp.initializeApp(options)
}*/

fun main() = application {

    //initializeFirebase()

    Window(onCloseRequest = ::exitApplication, title = "UARK HAZMat Capstone") {
        AppContent()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    AppContent()
}