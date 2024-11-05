plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    //Ksp
    id("com.google.devtools.ksp")
    //Hilt
    id("com.google.dagger.hilt.android")
    //Safe Args
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "ir.mahan.wikigames"
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.mahan.wikigames"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //RXJava3
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    // RXAdapter for Retrofit
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    // RXBinding
    implementation("com.jakewharton.rxbinding4:rxbinding:4.0.0")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    // Navigation Component
    val nav_version = "2.8.1"
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    // ROOM
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    //RX Room
    implementation("androidx.room:room-rxjava3:2.6.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // GSON
    implementation("com.google.code.gson:gson:2.10.1")
    // Coil: ImageLoading
    implementation("io.coil-kt:coil:2.6.0")
    // RxNetwork
    implementation("com.laimiux.rxnetwork:rxnetwork:0.0.4")
    // Joda Time
    implementation("net.danlew:android.joda:2.13.0")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
    // Material Components
    implementation("com.google.android.material:material:1.5.0")
    // Paging 3
    val paging_version = "3.3.2"
    implementation("androidx.paging:paging-runtime:$paging_version")
    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.7.3")
}