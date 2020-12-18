import org.bytedeco.javacpp.tools.Info

plugins {
    kotlin("jvm")
    id("com.google.osdetector")
    id("com.github.wumo.javacpp")
}

dependencies {
    api(project(":math"))
    testImplementation("junit:junit:4.13")
    testImplementation(kotlin("test-junit"))
    api("org.bytedeco:javacpp:1.5.3")
    implementation("org.bytedeco:javacpp-platform:1.5.3")
}

javacpp {
    include = listOf(
        "vkg/base/resource/texture_formats.h",
        "vkg/c/c_fpsmeter.h",
        "vkg/c/c_vec.h",
        "vkg/c/c_camera.h",
        "vkg/c/c_light.h",
        "vkg/c/c_primitive.h",
        "vkg/c/c_material.h",
        "vkg/c/c_mesh.h",
        "vkg/c/c_node.h",
        "vkg/c/c_model.h",
        "vkg/c/c_model_instance.h",
        "vkg/c/c_primitive_builder.h",
        "vkg/c/c_scene.h",
        "vkg/c/c_atmosphere.h",
        "vkg/c/c_shadowmap.h",
        "vkg/base/window/input.h",
        "vkg/c/c_panning_camera.h",
        "vkg/c/c_window.h",
        "vkg/base/call_frame_updater.hpp",
        "vkg/c/c_renderer.h"
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