plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    // Jetpack
    api(libs.compose.ui.core)

    // Compose Integration
    api(libs.navigation.compose)
}
