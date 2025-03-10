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
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.2.1-jre")

    api("org.bytedeco:llvm-platform:16.0.4-1.5.9")
    api("org.bytedeco:javacpp:1.5.9")
    api("org.bytedeco:llvm:16.0.4-1.5.9")
}

publishing {
    group = "com.github.happyzleaf"

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
