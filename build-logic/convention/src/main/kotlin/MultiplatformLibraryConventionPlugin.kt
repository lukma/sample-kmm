import com.android.build.gradle.LibraryExtension
import com.gplay.buildlogic.constant.AndroidConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with (pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                android()
            }

            extensions.configure<LibraryExtension> {
                compileSdk = AndroidConfig.compileSdk
                with(defaultConfig) {
                    minSdk = AndroidConfig.minSdk
                    targetSdk = AndroidConfig.targetSdk
                }
                sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
            }
        }
    }
}
