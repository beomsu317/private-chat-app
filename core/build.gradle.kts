apply {
    from("${rootDir}/base-module.gradle")
}

dependencies {
    "implementation"(AndroidX.coreKtx)

    "implementation"(Serialization.kotlinxSerializationJson)
    "implementation"(Serialization.kotlinxSerializationConverter)

    "implementation"(DataStore.dataStorePreferences)
    "implementation"(DataStore.dataStore)

    "implementation"(Network.retrofit)
    "implementation"(Network.okhttp)
}