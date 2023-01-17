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

    // Testing 
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.dbunit:dbunit:2.7.3")
	testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")


    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
	
	// https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
	implementation("com.mysql:mysql-connector-j:8.0.31")

	val springVersion = "5.3.24"
	// for JdbcTemplate
	implementation("org.springframework:spring-jdbc:$springVersion")
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
