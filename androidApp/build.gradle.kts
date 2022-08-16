import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("gplay.android.application")
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
        }
    }

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
    implementation(project(":shared:android-navigation"))
    implementation(project(":shared:android-uikit"))

    // Jetpack
    implementation(libs.androidx.startup)
    implementation(libs.bundles.compose.common)

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
    androidTestImplementation(libs.compose.ui.test.junit)
    androidTestImplementation(libs.mockk.android)
    debugImplementation(libs.bundles.test.compose.debug)
}
