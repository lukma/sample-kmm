plugins {
    id("gplay.android.library")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    // Jetpack
    implementation(libs.compose.ui.core)

    // Compose Integration
    implementation(libs.navigation.compose)
}
