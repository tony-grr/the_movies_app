package com.example.themoviesapp.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>?): String? {
        return intList?.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToListInt(data: String?): List<Int>? {
        return data?.split(",")?.mapNotNull { it.toIntOrNull() }
    }
}
