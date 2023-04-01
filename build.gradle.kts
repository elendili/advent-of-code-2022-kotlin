import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test"))
    implementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    implementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-scripting-jsr223
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.8.0-RC2")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}