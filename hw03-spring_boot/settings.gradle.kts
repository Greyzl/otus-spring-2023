rootProject.name = "hw03-spring_boot"

pluginManagement{
    val johnrengelmanShadow = "7.1.2"
    val dependencyManagement = "1.1.0"
    val springBoot = "3.0.6"
    plugins {
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springBoot

    }
}

