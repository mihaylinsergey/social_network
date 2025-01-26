plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.7.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    //metrics
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    //radis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    //websocket
    implementation ("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.webjars:stomp-websocket:2.3.4")

    //rabbit
    implementation("org.springframework.amqp:spring-rabbit:3.0.8")

    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named("compileJava") {
    dependsOn(tasks.openApiGenerate)
}

sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/java"))
    }
}

openApiGenerate {
    generatorName.set("java")
    configOptions.set(mapOf(
        "library" to "restclient",
        "openApiNullable" to "false"
    ))
    inputSpec.set("$rootDir/core/src/main/resources/specs/spec-v1.json")
    ignoreFileOverride.set(".openapi-generator-java-sources.ignore")
    invokerPackage.set("ru.otus.api.v1.invoker")
    modelPackage.set("ru.otus.api.v1.model")
    apiPackage.set("ru.otus.api.v1.api")
}
