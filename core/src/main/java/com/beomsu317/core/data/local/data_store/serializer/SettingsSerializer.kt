package com.beomsu317.core.data.local.data_store.serializer

import androidx.datastore.core.Serializer
import com.beomsu317.core.domain.model.Settings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class SettingsSerializer: Serializer<Settings> {

    override val defaultValue: Settings = Settings()

    override suspend fun readFrom(input: InputStream): Settings {
        return try {
            Json.decodeFromString(
                deserializer = Settings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Settings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}