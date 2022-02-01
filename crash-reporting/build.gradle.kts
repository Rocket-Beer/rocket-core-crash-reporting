plugins {
    id("kotlin")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")

    api("com.rocket.core:core-domain:0.0.3-alpha6")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.20")
    testImplementation("io.mockk:mockk:1.12.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
