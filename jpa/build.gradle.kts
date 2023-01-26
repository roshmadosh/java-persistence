plugins {
	war
	id("org.liquibase.gradle") version "2.1.1"
}

repositories {
	mavenCentral()
}


dependencies {
	val springVersion = "5.3.24"
	val hibernateVersion = "5.4.3.Final"
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("org.springframework:spring-webmvc:$springVersion")
	implementation("org.springframework:spring-orm:$springVersion")

	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")

	implementation("org.slf4j:slf4j-api:2.0.6")
	implementation("ch.qos.logback:logback-classic:1.4.5")

	implementation("org.aspectj:aspectjweaver:1.9.7")

	implementation("org.hibernate:hibernate-core:$hibernateVersion")
	implementation("org.hibernate:hibernate-validator:$hibernateVersion")
	implementation("com.h2database:h2:1.4.200")

	// needed by Validator bean for test to run
	implementation("org.glassfish.web:el-impl:2.2")

	liquibaseRuntime("com.mysql:mysql-connector-j:8.0.31")
	implementation("com.mysql:mysql-connector-j:8.0.31")
	liquibaseRuntime("info.picocli:picocli:4.6.3")
	liquibaseRuntime("org.liquibase:liquibase-core:4.19.0")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.springframework:spring-test:$springVersion")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}	

// Liquibase plugin configuration
liquibase {
    activities.register("main") {
        val db_url by project.extra.properties
        val db_user by project.extra.properties
        val db_password by project.extra.properties
		val changelog_location by project.extra.properties
        this.arguments = mapOf(
            "logLevel" to "info",
            "changeLogFile" to changelog_location,
            "url" to db_url,
            "username" to db_user,
            "password" to db_password,
            "driver" to "com.mysql.cj.jdbc.Driver"
        )
    }
    runList = "main"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<Test> {
    this.testLogging {
        this.showStandardStreams = true
    }
}
		

