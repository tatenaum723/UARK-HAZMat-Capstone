This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `HAZ-MAT Application` version containing all back-end code necessary for Bluetooth LE functionality. It has not been integrated into the data display screens due to hardware delays.
It was tested on a physical device to confirm the skeleton code works.
Modifications can be made easily to finish the data parsing and conversion should this project continue in future semesters.

* `To open the application` download the files and copy the contents of the zipped folder to a new directory. The next step requires you to have the current version of Android Studio installed. Open Android Studio and click the "File" or "Project" tab then select "Open". In the file explorer popup, navigate to the directory your files are stored in (The folder should have an Android robot icon next to it) and select \UARK-HAZMat-Capstone-[branch name]\composeApp\src\androidMain to open the Android application. From here you will have access to all files. The application can be built and run on an Android emulator or physical device using Android Studio's built-in features. You will also have the option to package the app as an apk file for deployment. 

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
