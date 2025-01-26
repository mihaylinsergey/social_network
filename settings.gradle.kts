pluginManagement {
    plugins {
        java
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "social_network"

include("dialog_service")
include("core")
include("dialog_service_redis")
include("count_service")