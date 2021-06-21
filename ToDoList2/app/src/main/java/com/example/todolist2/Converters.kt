package com.example.todolist2


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    private val zone: ZoneOffset = ZoneOffset.UTC
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
   /* open fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }*/
    fun toDate(timestamp: Long?): LocalDateTime? {
        return if (timestamp == null) null else LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), zone)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
 /*   fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }*/
    fun fromDate(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(zone)
    }

    @TypeConverter
    fun toImages(value:String) = enumValueOf<Images>(value)

    @TypeConverter
    fun fromImages(value: Images) = value.name

}