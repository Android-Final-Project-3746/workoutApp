package project.st991536629_st991576960.trung_yuxiao.framework.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch);
    }
}