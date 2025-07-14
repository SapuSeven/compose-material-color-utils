import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
}

dependencies {
    implementation(libs.error.prone.annotations)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

mavenPublishing {
    coordinates("com.sapuseven.compose", "materialcolorutils", "1.0.0")

    configure(JavaLibrary(
        javadocJar = JavadocJar.Javadoc(),
        sourcesJar = true,
    ))

    pom {
        name.set("Compose Material Color Utils")
        description.set("Lightweight utility to dynamically generate color schemes for Material Design 3 in Compose.")
        inceptionYear.set("2025")
        url.set("https://github.com/SapuSeven/compose-material-color-utils")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("sapuseven")
                name.set("SapuSeven")
                url.set("https://sapuseven.com")
            }
        }
        scm {
            url.set("https://github.com/SapuSeven/compose-material-color-utils")
            connection.set("scm:git:git://github.com/SapuSeven/compose-material-color-utils.git")
            developerConnection.set("scm:git:ssh://git@github.com/SapuSeven/compose-material-color-utils.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    if (!gradle.startParameter.taskNames.any { it.contains("publishToMavenLocal") }) {
        signAllPublications()
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SapuSeven/compose-material-color-utils")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

tasks {
    compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_17
        compilerOptions.freeCompilerArgs = listOf(
            "-opt-in=kotlin.contracts.ExperimentalContracts",
            "-Xjvm-default=all-compatibility",
        )
    }
    compileTestKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_17
    }
}
