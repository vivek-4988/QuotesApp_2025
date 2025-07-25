plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.viveks.quotesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.viveks.quotesapp"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.work.runtime.ktx)

    // Optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // Optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)

// Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)

    //koin
    // Koin core
    implementation(libs.koin.core)

    // Koin Android (ViewModel support)
    implementation(libs.koin.android)

    // Koin Compose integration
    implementation(libs.koin.androidx.compose)


    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    //kapt(libs.moshi.codegen)

    implementation(libs.retrofit)
    implementation(libs.moshi.converter)
    implementation(libs.logging.interceptor)

    // Coil
    implementation(libs.coil.compose)

    // Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

apply(plugin = "com.google.gms.google-services")