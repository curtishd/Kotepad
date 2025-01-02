plugins {
    kotlin("jvm") version "2.0.21"
}

group = "me.cdh"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.weisj:jsvg:1.4.0")
    implementation("com.formdev:flatlaf-extras:3.5.4")
    implementation("com.formdev:flatlaf:3.5.4")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks {
    compileJava {
        dependsOn(compileKotlin)
        doFirst {
            options.compilerArgs = listOf(
                "--module-path", classpath.asPath
            )
        }
    }
    compileKotlin {
        destinationDirectory.set(compileJava.get().destinationDirectory)
    }
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "me.cdh.Main")
        )
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
task("copyDependencies", Copy::class) {
    configurations.compileClasspath.get()
        .filter { it.extension == "jar" }
        .forEach { from(it.absolutePath).into("${layout.buildDirectory.get()}/libs") }
}