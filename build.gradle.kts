// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    //Ksp
    id("com.google.devtools.ksp") version "2.0.10-1.0.24" apply false
    //Hilt
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
    //Safe Args
    id ("androidx.navigation.safeargs") version "2.8.1" apply false
}