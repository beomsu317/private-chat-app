plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

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

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)
    implementation(DaggerHilt.hiltNavigationCompose)

    implementation(Compose.composeUi)
    implementation(Compose.composeMaterial)

    implementation(Serialization.kotlinxSerializationJson)

    implementation(Accompanist.systemUiController)

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))

    implementation(project(Modules.startupData))
    implementation(project(Modules.startupDomain))
    implementation(project(Modules.startupPresentation))

    implementation(project(Modules.friendsData))
    implementation(project(Modules.friendsDomain))
    implementation(project(Modules.friendsPresentation))

    implementation(project(Modules.chatData))
    implementation(project(Modules.chatDomain))
    implementation(project(Modules.chatPresentation))

    implementation(project(Modules.profileData))
    implementation(project(Modules.profileDomain))
    implementation(project(Modules.profilePresentation))
}