plugins {
    id("java")
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "sesel"
version = "0.1.0"
description = "Sesel Geracao PDF"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    gradlePluginPortal()
    mavenCentral()
}

configurations{
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.5")

    implementation ("com.github.librepdf:openpdf:1.3.26")
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")

    compileOnly("org.projectlombok:lombok:1.18.20")
    testCompileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.20")

    implementation("org.apache.commons:commons-text:1.9")
    implementation ("com.google.guava:guava:31.0.1-jre")
    implementation("com.github.javafaker:javafaker:1.0.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
