import org.gradle.kotlin.dsl.testImplementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)  // Hilt plugin
    alias(libs.plugins.kapt.android)
}

android {
    namespace = "com.dev.aravp.weatherapp_mvvm_clean_architecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dev.aravp.weatherapp_mvvm_clean_architecture"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Enable ViewBinding and DataBinding
    buildFeatures {
        viewBinding = true       // Enable ViewBinding
        dataBinding = true      // Enable DataBinding
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

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Dagger Hilt for Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // WorkManager for background tasks
    implementation(libs.androidx.work.runtime.ktx)

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.junit:junit-ktx:1.1.5")
    androidTestImplementation("androidx.espresso:espresso-core:3.5.1")

    // Hilt for testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    //kaptAndroidTest('com.google.dagger:hilt-android-compiler:2.44')
}

/*
kapt {
    correctErrorTypes = true
}*/
