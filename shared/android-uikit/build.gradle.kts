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
    // Internal Module
    implementation(project(":shared:common"))

    // Jetpack
    implementation(libs.compose.ui.core)
    implementation(libs.constraint.compose)
    implementation(libs.paging.compose)
    implementation(libs.lottie.compose)

    // Material
    implementation(libs.material3.compose)
    implementation(libs.material.compose.icon)
}
