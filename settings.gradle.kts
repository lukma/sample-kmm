pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "GPlay"
include(
    ":shared:common",
    ":shared:android-navigation",
    ":shared:android-uikit",
    ":androidApp",
)
