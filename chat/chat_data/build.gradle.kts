apply {
    from("${rootDir}/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.chatDomain))

    "implementation"(Network.retrofit)
    "implementation"(Network.okhttp)

    "implementation"(Serialization.kotlinxSerializationJson)
    "implementation"(Serialization.kotlinxSerializationConverter)

    "implementation"(Room.roomKtx)
    "api"(Room.roomRuntime)
    "kapt"(Room.roomCompiler)
}