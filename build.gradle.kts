plugins {

    kotlin("multiplatform") version "1.8.0"

    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "1.2.0"

}

group = "io.h4kt"
version = "1.0"

val okioVersion: String by project

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        jvmToolchain(8)
        withJava()
    }

    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")

    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                api("com.squareup.okio:okio:$okioVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                api("com.squareup.okio:okio-nodefilesystem:$okioVersion")
            }
        }

        val jvmMain by getting
        val nativeMain by getting

    }

}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/H4kt/okio-wrappers")
            credentials {
                username = System.getenv("GITHUB_USERNAME") ?: env.GITHUB_USERNAME.orNull()
                password = System.getenv("GITHUB_ACCESS_TOKEN") ?: env.GITHUB_ACCESS_TOKEN.orNull()
            }
        }
    }

    publications.withType<MavenPublication> {

//        artifact(javadocJar.get())

        pom {

            name.set("okio-wrappers")
            url.set("https://maven.pkg.github.com/H4kt/okio-wrappers")

        }

    }

}
