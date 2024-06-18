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

        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
        testCompileOnly("org.projectlombok:lombok:1.18.32")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

        compileOnly("org.jetbrains:annotations:24.0.1")
        testCompileOnly("org.jetbrains:annotations:24.0.1")

        implementation("com.google.guava:guava:33.2.1-jre")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
