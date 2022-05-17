apply {
    from("${rootDir}/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.friendsDomain))

    "implementation"(Coil.coilCompose)
    "implementation"(Swipe.swipe)
    "implementation"(AndroidX.coreKtx)

    "implementation"(Accompanist.swipeRefresh)

}