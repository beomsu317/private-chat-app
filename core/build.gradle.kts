apply {
    from("${rootDir}/base-module.gradle")
}

dependencies {
    "implementation"(AndroidX.coreKtx)
    "implementation"(Serialization.kotlinxSerializationJson)
    "implementation"(DataStore.dataStorePreferences)
}