import org.bytedeco.javacpp.tools.Info

plugins {
  base
  `maven-publish`
  kotlin("jvm") version "1.3.72"
  id("com.google.osdetector") version "1.6.2"
  id("com.github.wumo.javacpp") version "1.0.13"
}

group = "com.github.wumo"
version = "0.0.1"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  testImplementation("junit:junit:4.13")
  testImplementation(kotlin("test-junit"))
  api("org.bytedeco:javacpp:1.5.3")
  implementation("org.bytedeco:javacpp-platform:1.5.3")
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}

javacpp {
  include = listOf(
      "c/c_vec.h",
      "c/c_camera.h",
      "c/c_light.h",
      "c/c_primitive.h",
      "c/c_material.h",
      "c/c_mesh.h",
      "c/c_node.h",
      "c/c_model.h",
      "c/c_model_instance.h",
      "c/c_primitive_builder.h",
      "c/c_scene_manager.h",
      "c/c_atmosphere.h",
      "vkez/window/input.h",
      "c/c_panning_camera.h",
      "c/c_window.h",
      "vkez/call_frame_updater.h",
      "c/c_renderer.h"
  )
  link = listOf("vkg")
  target = "com.github.wumo.vkg.graphics.VkgNative"
  infoMap = {
    it.put(Info("CallFrameUpdater").virtualize())
    it.put(Info("cvec2").cast().pointerTypes("cvec2", "float[]"))
    it.put(Info("cvec3").cast().pointerTypes("cvec3", "float[]"))
    it.put(Info("cvec4").cast().pointerTypes("cvec4", "float[]"))
    it.put(Info("cmat3").cast().pointerTypes("cmat3", "float[]"))
    it.put(Info("cmat4").cast().pointerTypes("cmat4", "float[]"))
    it.put(Info("ctransform").cast().pointerTypes("ctransform", "float[]"))
    it.put(Info("caabb").cast().pointerTypes("caabb", "float[]"))
  }
  cppSourceDir = "${project.projectDir}/src/main/cpp"
  cppIncludeDir = "$cppSourceDir/src"
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