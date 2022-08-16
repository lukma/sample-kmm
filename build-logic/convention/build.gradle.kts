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
    }
}
