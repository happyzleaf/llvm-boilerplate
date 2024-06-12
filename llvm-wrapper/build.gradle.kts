plugins {
    id("java")
}

group = "org.voidlang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.bytedeco:llvm-platform:16.0.4-1.5.9")
    implementation("org.bytedeco:javacpp:1.5.9")
    implementation("org.bytedeco:llvm:16.0.4-1.5.9")
    // implementation("org.bytedeco:libffi-platform:3.4.4-1.5.10-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
