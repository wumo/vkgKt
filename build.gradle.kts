import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  `maven-publish`
  application
  kotlin("jvm") version "1.4.10" apply false
  id("com.google.osdetector") version "1.6.2" apply false
  id("com.github.wumo.javacpp") version "1.0.16" apply false
}

allprojects {
  apply(plugin = "maven-publish")
  apply(plugin = "org.jetbrains.kotlin.jvm")

  group = "com.github.wumo"
  version = "0.2.2"

  repositories {
    jcenter()
    mavenCentral()
  }

  dependencies {
    testImplementation("junit:junit:4.13")
    testImplementation(kotlin("test-junit"))
  }

  tasks.test {
    useJUnitPlatform()
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }

  val kotlinSourcesJar by tasks

  publishing {
    publications {
      create<MavenPublication>("maven") {
        from(components["kotlin"])
        artifact(kotlinSourcesJar)
      }
    }
  }
}