plugins {
    id("java")
    id ("com.github.johnrengelman.shadow")
    id ("io.spring.dependency-management")
}


group = "ru.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val springBoot = "3.0.6"
val openCsvVersion = "5.7.1"
val apacheCommonsVersion = "3.12.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.opencsv:opencsv")
    implementation("org.apache.commons:commons-lang3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}



dependencyManagement {
    dependencies {
        dependency("com.opencsv:opencsv:${openCsvVersion}")
        dependency("org.apache.commons:commons-lang3:${apacheCommonsVersion}")
        dependency("org.springframework.boot:spring-boot-starter:${springBoot}")
        dependency("org.springframework.boot:spring-boot-starter-test:${springBoot}")
    }
    imports {
    }
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