import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.gplay.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            extensions.configure<BaseAppModuleExtension>(::configureAndroidCompose)
        }
    }
}