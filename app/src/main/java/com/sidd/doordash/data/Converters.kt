package com.sidd.doordash.data

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Converters for database.
 */
class Converters {

    /**
     * Get [Status] object from string
     */
    @TypeConverter
    fun statusFromString(value: String): Status {
        return Gson().fromJson(value, Status::class.java)
    }

    /**
     * Get json string
     */
    @TypeConverter
    fun stringFromStatus(status: Status): String? {
        return Gson().toJson(status)
    }
}