package com.gplay.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().toString()
        }
    }

    dependencies {
        // Compose
        implementation(libs.findLibrary("compose.ui.core").get())
        implementation(libs.findLibrary("compose.ui.tooling.preview").get())

        // Testing
        androidTestImplementation(libs.findLibrary("compose.ui.test.junit").get())
        debugImplementation(libs.findLibrary("compose.ui.tooling").get())
        debugImplementation(libs.findLibrary("compose.ui.test.manifest").get())
    }
}
