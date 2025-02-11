plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "org.voidlang"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.2.1-jre")

    implementation("org.bytedeco:llvm-platform:16.0.4-1.5.9")
    implementation("org.bytedeco:javacpp:1.5.9")
    implementation("org.bytedeco:llvm:16.0.4-1.5.9")
}
