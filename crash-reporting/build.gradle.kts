import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("java-library")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.3")
    testImplementation("com.google.truth:truth:1.0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.2.60")
    testImplementation("io.mockk:mockk:1.12.0")
}

publishing {
    repositories {
        maven {
            name = "Rocket-Core_crash-reporting"
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
                groupId = "com.rocket.core"
                artifactId = "crash-reporting"
                version = "0.0.1"
                artifact("$buildDir/libs/$artifactId.jar")
            }
        }
    }
}
