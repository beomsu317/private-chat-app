apply {
    from("${rootDir}/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.startupDomain))

    "implementation"(Network.retrofit)
    "implementation"(Network.okhttp)

    "implementation"(Serialization.kotlinxSerializationJson)
    "implementation"(Serialization.kotlinxSerializationConverter)
}