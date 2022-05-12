plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.beomsu317.privatechatapp"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")

    implementation(Compose.composeUi)
    implementation(Compose.composeMaterial)
    implementation(Compose.composeUiToolingPreview)
    debugImplementation(Compose.composeUiTooling)
    androidTestImplementation(Compose.composeUiTestJunit4)

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.navigation:navigation-compose:2.4.2")

    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.7-alpha")

    implementation("androidx.datastore:datastore:1.0.0")

    implementation("io.coil-kt:coil-compose:2.0.0-rc03")

    implementation("com.google.accompanist:accompanist-permissions:0.24.7-alpha")

    implementation("me.saket.swipe:swipe:1.0.0")
}