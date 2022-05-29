apply {
    from("${rootDir}/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.chatDomain))

    "implementation"(Serialization.kotlinxSerializationJson)

    "implementation"(Coil.coilCompose)

    "implementation"("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")
//    "implementation"("com.google.accompanist:accompanist-insets:0.24.9-beta")

}