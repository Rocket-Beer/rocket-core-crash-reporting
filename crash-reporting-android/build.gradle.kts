import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)

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

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation("androidx.core:core-ktx:1.6.0")

    implementation("com.rocket.core:crash-reporting:0.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.3")
    testImplementation("com.google.truth:truth:1.0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.2.60")
    testImplementation("io.mockk:mockk:1.12.0")
}

publishing {
    repositories {
        maven {
            name = "rocket-core-crash-reporting"
            url = uri("https://maven.pkg.github.com/Rocket-Beer/rocket-core-crash-reporting")
            credentials {
                username = gradleLocalProperties(rootDir).getProperty("github.username")
                password = gradleLocalProperties(rootDir).getProperty("github.password")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.rocket.android.core"
                artifactId = "crash-reporting-android"
                version = "0.0.1"
                artifact("$buildDir/outputs/aar/$artifactId-debug.aar")
            }
        }
    }
}
