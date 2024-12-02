plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("kapt") version "1.9.25"
}

group = "marcelodev"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// MYSQL DATABASE
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("mysql:mysql-connector-java:8.0.33")

	// Google Maps
	implementation("com.google.maps:google-maps-services:2.1.2")

	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")

	// Firebase
	implementation("com.google.firebase:firebase-admin:9.2.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.reactivestreams:reactive-streams:1.0.3")

	// FLYWAY
	implementation("org.flywaydb:flyway-core:9.22.0")
	implementation("org.flywaydb:flyway-mysql:9.22.0")

	// DOZER
	implementation("org.modelmapper:modelmapper:3.1.0")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.auth0:java-jwt:4.2.1")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// SPRING BOOT
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
