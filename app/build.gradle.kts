plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    hilt plugin
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

//    room plugin
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.anilab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.anilab"
        minSdk = 29
        //noinspection EditedTargetSdkVersion
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit for HTTP requests
    implementation(libs.retrofit)
    // Gson converter for Retrofit
    implementation(libs.converter.gson)

//    hilt dependency
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

//    livedata dependency for observe as state
    implementation(libs.androidx.runtime.livedata)

//    coil dependency
    implementation(libs.coil.compose)

//    Bottom nav bar dependency
    implementation(libs.androidx.navigation.compose.v277)


//room dependencies
    // Room runtime
    implementation ("androidx.room:room-runtime:2.6.1") // Replace with the latest version

    implementation ("androidx.room:room-ktx:2.6.1")


    // Room Kotlin Extensions and Coroutines support
    ksp("androidx.room:room-compiler:2.6.1")




}