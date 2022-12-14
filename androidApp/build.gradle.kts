import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("gplay.android.application")
    id("gplay.android.application.compose")
}

android {
    defaultConfig {
        applicationId = "com.gplay.app"
        versionCode = 1
        versionName = "1.0.0-alpha01"
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"

            val apiProp = Properties().apply {
                load(FileInputStream(file("../apis.properties")))
            }
            buildConfigField(
                "String",
                "API_HOST",
                "\"${apiProp.getProperty("DEV_API_HOST")}\""
            )
            buildConfigField(
                "String",
                "API_PREFIX_PATH",
                "\"${apiProp.getProperty("DEV_API_PREFIX_PATH")}\""
            )
        }
    }
}

dependencies {
    // Internal Module
    implementation(project(":shared:common"))
    implementation(project(":shared:compose-navigation"))
    implementation(project(":shared:compose-uikit"))

    // Androidx
    implementation(libs.androidx.startup)

    // Material
    implementation(libs.material3.compose)
    implementation(libs.material.compose.icon)

    // Compose Integration
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.constraint.compose)
    implementation(libs.paging.compose)
    implementation(libs.coil.compose)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Accompanist
    implementation(libs.accompanist.placeholder)

    // Testing
    testImplementation(libs.mockk.core)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.mockk.android)
}
