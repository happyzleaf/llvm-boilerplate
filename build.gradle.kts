plugins {
    id("java")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "org.voidlang"
version = System.getenv("VERSION") ?: "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    compileOnly("org.jetbrains:annotations:24.0.1")
    testCompileOnly("org.jetbrains:annotations:24.0.1")

    implementation("com.google.guava:guava:33.2.1-jre")

    implementation("org.bytedeco:llvm-platform:16.0.4-1.5.9")
    implementation("org.bytedeco:javacpp:1.5.9")
    implementation("org.bytedeco:llvm:16.0.4-1.5.9")
    // implementation("org.bytedeco:libffi-platform:3.4.4-1.5.10-SNAPSHOT")
}
