pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id("io.quarkus") version "3.12.3"
    }
}

plugins {
    id("com.gradle.develocity") version("3.17.5")
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
        publishing.onlyIf { false }
    }
}

rootProject.name = "quarkus-blog"
