plugins {
    id("gplay.android.library")
    id("gplay.android.library.compose")
}

dependencies {
    // Internal Module
    implementation(project(":shared:common"))

    // Compose Integration
    implementation(libs.constraint.compose)
    implementation(libs.paging.compose)
    implementation(libs.lottie.compose)

    // Material
    implementation(libs.material3.compose)
    implementation(libs.material.compose.icon)
}
