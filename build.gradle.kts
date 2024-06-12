plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "org.voidlang"
version = System.getenv("VERSION") ?: "1.0-SNAPSHOT"

allprojects {
    if (!project.buildFile.isFile || project == rootProject)
        return@allprojects

    plugins.apply("java-library")
    plugins.apply("java")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
