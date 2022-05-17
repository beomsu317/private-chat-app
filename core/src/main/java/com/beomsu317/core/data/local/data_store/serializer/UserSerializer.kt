package com.beomsu317.core.data.local.data_store.serializer

import androidx.datastore.core.Serializer
import com.beomsu317.core.domain.model.Settings
import com.beomsu317.core.domain.model.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserSerializer : Serializer<User> {

    override val defaultValue: User = User()

    override suspend fun readFrom(input: InputStream): User {
        return try {
            Json.decodeFromString(
                deserializer = User.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = User.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}