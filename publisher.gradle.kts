import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("maven-publish")
}

fun getPublisherUsername(): String {
    return gradleLocalProperties(rootDir).getProperty("github.username") ?: System.getenv("GITHUB_PACKAGE_PUBLISHER_USERNAME")
}

fun getPublisherPassword(): String {
    return gradleLocalProperties(rootDir).getProperty("github.password") ?: System.getenv("GITHUB_PACKAGE_PUBLISHER_PASSWORD")
}

tasks.register("publishToGithubPackages") {
    group = "publishing"
    description = "Publishes all Maven publications to GithubPackages."
}

publishing {
    repositories {
        maven {
            name = "rocket-core-crash-reporting"
            url = uri("https://maven.pkg.github.com/Rocket-Beer/rocket-core-crash-reporting")
            credentials {
                username = getPublisherUsername()
                password = getPublisherPassword()
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