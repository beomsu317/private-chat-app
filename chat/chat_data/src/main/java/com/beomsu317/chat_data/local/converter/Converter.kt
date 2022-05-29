package com.beomsu317.chat_data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {
    @TypeConverter
    fun listToJsonString(value: Set<String>) = Json.encodeToString(value)

    @TypeConverter
    fun listFromJsonString(value: String) = Json.decodeFromString<Set<String>>(value)

}