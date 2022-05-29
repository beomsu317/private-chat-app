apply {
    from("${rootDir}/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.startupDomain))
    "implementation"(project(Modules.chatDomain))
}