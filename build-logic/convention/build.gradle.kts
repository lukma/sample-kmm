plugins {
    `kotlin-dsl`
}

group = "com.gplay.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.android.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "gplay.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "gplay.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "gplay.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "gplay.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("multiplatformLibrary") {
            id = "gplay.multiplatform.library"
            implementationClass = "MultiplatformLibraryConventionPlugin"
        }
    }
}
