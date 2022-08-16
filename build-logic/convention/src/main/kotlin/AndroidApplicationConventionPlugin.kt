import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.gplay.buildlogic.androidTestImplementation
import com.gplay.buildlogic.configureKotlinAndroid
import com.gplay.buildlogic.implementation
import com.gplay.buildlogic.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.application")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)
                with(defaultConfig) {
                    targetSdk = 33
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables.useSupportLibrary = true
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

                packagingOptions {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                // Kotlin
                implementation(libs.findLibrary("kotlinx.datetime").get())

                // Jetpack
                implementation(libs.findLibrary("androidx.core").get())

                // Testing
                testImplementation(libs.findLibrary("junit").get())
                testImplementation(libs.findLibrary("kotlin.test").get())
                testImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
                androidTestImplementation(libs.findLibrary("androidx.test.junit").get())
                androidTestImplementation(libs.findLibrary("espresso.core").get())
            }
        }
    }
}
