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

	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("com.mysql:mysql-connector-j:8.0.31")
	implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	implementation("org.slf4j:slf4j-api:2.0.6")
	implementation("ch.qos.logback:logback-classic:1.4.5")
	implementation("org.hibernate.orm:hibernate-core:6.1.6.Final")
}
