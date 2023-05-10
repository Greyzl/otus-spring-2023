plugins {
    id("java")
    id ("com.github.johnrengelman.shadow")
}


group = "ru.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val springVersion = "6.0.7"
val openCsvVersion = "5.7.1"
val junitVersion = "5.8.1"
val apacheCommonsVersion = "3.12.0"
val mockitoVersion = "5.3.1"

dependencies {
    implementation("org.springframework:spring-context:${springVersion}")
    implementation("com.opencsv:opencsv:${openCsvVersion}")
    implementation("org.apache.commons:commons-lang3:${apacheCommonsVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("questionApp")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.Main"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}