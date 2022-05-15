apply {
    from("${rootDir}/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.profileDomain))

    "implementation"(Network.retrofit)
    "implementation"(Network.okhttp)

    "implementation"(Room.roomRuntime)
    "implementation"(Room.roomKtx)
    "kapt"(Room.roomCompiler)

    "implementation"(Serialization.kotlinxSerializationJson)
    "implementation"(Serialization.kotlinxSerializationConverter)
}