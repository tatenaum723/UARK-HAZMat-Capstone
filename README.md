This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

*This HAZ-MAT Application version contains all back-end code necessary for Bluetooth LE functionality. It has not been integrated into the data display screens due to the hardware delays.
*It was tested on a physical device to confirm the skeleton code works.
*Modification can be made easily to finish the data parsing and conversion should this project continue in future semesters.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
