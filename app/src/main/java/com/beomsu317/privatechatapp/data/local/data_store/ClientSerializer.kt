package com.beomsu317.privatechatapp.data.local.data_store

import androidx.datastore.core.Serializer
import com.beomsu317.privatechatapp.domain.model.Client
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ClientSerializer : Serializer<Client> {
    override val defaultValue: Client
        get() = Client()

    override suspend fun readFrom(input: InputStream): Client {
        return try {
            Json.decodeFromString<Client>(input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(client: Client, output: OutputStream) {
        output.write(
            Json.encodeToString(client).encodeToByteArray()
        )
    }
}