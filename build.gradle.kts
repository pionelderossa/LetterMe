// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false


    //apply false
    //
    //This means that the plugin is not applied at the root level, but it can be manually applied in specific modules where needed.
}