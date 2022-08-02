import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    kotlin("android")
    id("com.android.application")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.gplay.app"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf(
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        )
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Internal Module
    implementation(project(":shared:common"))
    implementation(project(":shared:android-navigation"))

    // Jetpack
    implementation(libs.androidx.core)
    implementation(libs.androidx.startup)
    implementation(libs.bundles.compose.common)

    // Compose Integration
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.constraint.compose)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.core)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit)
    androidTestImplementation(libs.mockk.android)
    debugImplementation(libs.bundles.test.compose.debug)
}
