plugins {
	application
	war
}

repositories {
	mavenCentral()
}

application {
    mainClass.set("com.hiroshisprojects.jpa.Main") 
}

dependencies {
	val springVersion = "5.3.24"
	val hibernateVersion = "5.4.3.Final"
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("org.springframework:spring-webmvc:$springVersion")
	implementation("com.mysql:mysql-connector-j:8.0.31")
	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	implementation("org.slf4j:slf4j-api:2.0.6")
	implementation("ch.qos.logback:logback-classic:1.4.5")
	implementation("org.hibernate:hibernate-core:$hibernateVersion")
	implementation("org.springframework:spring-orm:$springVersion")
	implementation("org.hibernate:hibernate-validator:$hibernateVersion")
	
}
