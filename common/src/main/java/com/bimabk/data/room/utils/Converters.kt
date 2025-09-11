package com.bimabk.data.room.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun listToString(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun stringToList(value: String) = Json.decodeFromString<List<String>>(value)
}