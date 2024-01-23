// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false

    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.google.dagger.hilt.android") version "2.50" apply false

}
true // Needed to make the Suppress annotation work for the plugins block