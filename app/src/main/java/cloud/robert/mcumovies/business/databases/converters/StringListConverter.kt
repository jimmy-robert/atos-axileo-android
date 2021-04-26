package cloud.robert.mcumovies.business.databases.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String) = value.split(",")

    @TypeConverter
    fun toString(value: List<String>) = value.joinToString(",")
}