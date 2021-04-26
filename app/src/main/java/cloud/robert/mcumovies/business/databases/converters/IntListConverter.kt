package cloud.robert.mcumovies.business.databases.converters

import androidx.room.TypeConverter

class IntListConverter {
    @TypeConverter
    fun fromString(value: String) = value.split(",").map { it.toInt() }

    @TypeConverter
    fun toString(value: List<Int>) = value.joinToString(",")
}