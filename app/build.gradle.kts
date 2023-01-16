plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
	application
	war
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}


dependencies {
	// servlet
	compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
	
	// https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
	implementation("com.mysql:mysql-connector-j:8.0.31")

	val springVersion = "5.3.24"
	// spring web mvc 
	implementation("org.springframework:spring-webmvc:$springVersion")

	// jackson
	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

	// DB connection pool
	implementation("com.zaxxer:HikariCP:5.0.1")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
