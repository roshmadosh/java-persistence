import com.bmuschko.gradle.tomcat.extension.TomcatPluginExtension

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    java
	id("com.bmuschko.tomcat") version "2.7.0"
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

	// tomcat	
    val tomcatVersion = "9.0.43"
    tomcat("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    tomcat("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")

	val springVersion = "5.3.24"
	// spring web mvc 
	implementation("org.springframework:spring-webmvc:$springVersion")

	// jackson
	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
}


configure<TomcatPluginExtension> {
    httpProtocol = "org.apache.coyote.http11.Http11Nio2Protocol"
    ajpProtocol = "org.apache.coyote.ajp.AjpNio2Protocol"
}


tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.war {
	archiveFileName.set("jdbc.war")
}
