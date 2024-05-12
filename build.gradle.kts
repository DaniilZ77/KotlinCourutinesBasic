import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "intro-coroutines"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(libs.kotlin.serialization.json)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)
    implementation(libs.coroutines.debug)
    implementation(libs.coroutines.slf4j)

    implementation(libs.logback)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.mock)
    implementation(libs.retrofit.rxjava2)
    implementation(libs.retrofit.kotlinx.serialization)

    implementation(libs.okhttp)
    implementation(libs.rxjava)
    implementation(libs.rxkotlin)

    testImplementation(kotlin("test"))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add(
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("src")
            resources.srcDir("resource")
        }
        test {
            kotlin.srcDir("test")
        }
    }

    jvmToolchain(JavaVersion.VERSION_21.majorVersion.toInt())
}
