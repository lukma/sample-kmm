pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "GPlay"
include(
    ":shared:common",
    ":shared:android-navigation",
    ":androidApp",
)
