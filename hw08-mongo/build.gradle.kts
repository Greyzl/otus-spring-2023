plugins {
    java
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "ru.otus"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["springShellVersion"] = "3.0.2"
val flapDoodleVersion = "4.7.0"
val mongock = "5.3.1"


dependencies {
    implementation("org.springframework.shell:spring-shell-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x")
    implementation("io.mongock:mongock-springboot-v3")
    implementation("io.mongock:mongodb-springdata-v4-driver")
    implementation("io.mongock:mongock-springboot-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
        mavenBom("io.mongock:mongock-bom:${mongock}")
    }
    dependencies{
        dependency("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${flapDoodleVersion}")
        dependency("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:${flapDoodleVersion}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
