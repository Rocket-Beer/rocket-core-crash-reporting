plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(32)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.rocket.android.core.myapplication"
        minSdkVersion(24)
        targetSdkVersion(32)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.20")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-native-mt")

    implementation("com.rocket.core:core-domain:0.0.3-alpha6")
    implementation("com.rocket.core:crash-reporting:0.0-beta0")
    implementation("com.rocket.android.core:crash-reporting-android-file:0.0.3-alpha8")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
