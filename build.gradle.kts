plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.spotless)
    alias(libs.plugins.lombok)
    idea
    `maven-publish`
}

val ENV = System.getenv()
val runNumber = ENV["GITHUB_RUN_NUMBER"] ?: "9999"
val isRelease = project.hasProperty("release")

val mod_version: String by project
val mod_id: String by project
val maven_group: String by project

version = if (isRelease) "${mod_version}" else "${mod_version}-build.${runNumber}"

group = maven_group

base.archivesName = mod_id

repositories {
    val repositories =
            setOf(
                    "https://maven.parchmentmc.org",
                    "https://maven.terraformersmc.com/",
                    "https://mvn.devos.one/snapshots/",
                    "https://maven.jamieswhiteshirt.com/libs-release",
                    "https://jitpack.io",
                "https://api.modrinth.com/maven",
            )

    repositories.forEach { maven { url = uri(it) } }
    mavenCentral()
}

sourceSets {
    register("testmod") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

loom {
    accessWidenerPath = file("src/main/resources/codebebelib.accesswidener")

    runs {
        register("testmodClient") {
            client()
            name = "Testmod Client"
            source(sourceSets["testmod"])
        }

        register("testmodServer") {
            server()
            name = "Testmod Server"
            source(sourceSets["testmod"])
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.18.2:2022.11.06@zip")
        }
    )

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.kotlin)

    modLocalRuntime(libs.modmenu) { exclude(group = "net.fabricmc") }
    modLocalRuntime(libs.lazydfu)


}

tasks.withType<ProcessResources>().configureEach {
    val mod_name: String by project
    val mod_id: String by project
    val mod_license: String by project
    val mod_version: String by project
    val mod_description: String by project

    val replaceProperties =
        hashMapOf(
            "loader_version" to libs.versions.fabric.loader.get(),
            "minecraft_version" to libs.versions.minecraft.get(),
            "mod_id" to mod_id,
            "mod_name" to mod_name,
            "mod_license" to mod_license,
            "mod_version" to mod_version,
            "mod_description" to mod_description,
        )

    inputs.properties(replaceProperties)

    filesMatching(setOf("fabric.mod.json")) { expand(replaceProperties) }
}

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }

    withSourcesJar()
}


tasks.named<Jar>("jar") {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = mod_id
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        val mavenUsername = ENV["MAVEN_USERNAME"]
        val mavenPassword = ENV["MAVEN_PASSWORD"]

        if (isRelease) {
            maven {
                url = uri("https://maven.tobynguyen.dev/releases")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        } else {
            maven {
                url = uri("https://maven.tobynguyen.dev/snapshots")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}

spotless {
    encoding("UTF-8")

    java {
        target("src/main/java/**/*.java", "src/test/java/**/*.java")

        toggleOffOn()

        removeUnusedImports("cleanthat-javaparser-unnecessaryimport")
        endWithNewline()
        palantirJavaFormat()
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")

        biome("2.3.7").downloadDir( File(rootDir, ".gradle/biome").absolutePath).configPath( File(rootDir, "spotless/biome.json").absolutePath)

        endWithNewline()
    }

    format("misc") {
        target(".gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }
}


idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}