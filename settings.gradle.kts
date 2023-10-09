pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GameZoom"
include(
    ":app",
    ":base",
    ":jumb_boy",
    ":knife",
    ":block_puzzle",
    ":game2048",
    ":free_draw_puzzle",
    ":puzzle15app",
)
 