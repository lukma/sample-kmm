pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "GPlay"
include(
    ":shared:common",
    ":shared:compose-navigation",
    ":shared:android-uikit",
    ":androidApp",
)
