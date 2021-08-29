plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("java")
	id("org.flywaydb.flyway") version "7.14.0"
}

group = "com.odana"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("mysql:mysql-connector-java")
	implementation("org.springframework.boot:spring-boot-starter-security")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.mapstruct:mapstruct:1.4.2.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testCompileOnly("org.mockito:mockito-junit-jupiter")
	testCompileOnly("org.assertj:assertj-core")
}

flyway {
	url = "jdbc:mysql://localhost:3306/mysql"
	user = "{yourMySQLDatabaseUserName}"
	password = "{yourMySQLDatabasePassword}"
	schemas = arrayOf("costRegister")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<JavaCompile> {
	dependsOn("flywayMigrate")
}
