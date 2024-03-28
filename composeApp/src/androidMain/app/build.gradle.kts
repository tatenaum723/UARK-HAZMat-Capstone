plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.hazmatapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hazmatapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //dagger hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-compiler:2.50")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")


    //Kotlin Crypto
    val core = "0.4.0"
    implementation("org.kotlincrypto.core:digest:$core")
    implementation("org.kotlincrypto.core:mac:$core")
    implementation("org.kotlincrypto.core:xof:$core")
    // define the BOM and its version
    implementation(platform("org.kotlincrypto.macs:bom:0.4.0"))
    // HmacSHA224, HmacSHA256, HmacSHA384, HmacSHA512
    // HmacSHA512/t, HmacSHA512/224, HmacSHA512/256
    implementation("org.kotlincrypto.macs:hmac-sha2")


    //Permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.21.1-beta")

}

kapt{
    correctErrorTypes = true
}