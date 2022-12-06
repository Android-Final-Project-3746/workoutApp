package project.st991536629_st991576960.trung_yuxiao.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatUtil {
    companion object {
        fun extractDate(dateTime: Date): String {
            val pattern = "EEE, dd MMMM yyyy"
            val localDateTime = convertDateToLocalDate(dateTime)
            val localDate = localDateTime.toLocalDate()
            val result: String = "${localDate.format(DateTimeFormatter.ofPattern(pattern))}"

            return result
        }

        fun extractTime(dateTime: Date): String {
            val pattern = "hh:mm a";

            val localDateTime = convertDateToLocalDate(dateTime)
            val localTime = localDateTime.toLocalTime();
            val result : String = "${localTime.format(DateTimeFormatter.ofPattern(pattern))}"

            return result;
        }

        fun convertDateToLocalDate(dateTime: Date): LocalDateTime {
            return Instant.ofEpochMilli(dateTime.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }

        fun getTimeStringFromDateTime(dateTime: Date): String {
            val pattern = "hh:mm a"

            val localDateTime = convertDateToLocalDate(dateTime)
            val localTime = localDateTime.toLocalTime();
            val result : String = "${localTime.format(DateTimeFormatter.ofPattern(pattern))}"

            return result;
        }

        fun compareCurrentDateToDate(dateTime: Date) : Boolean {

            val currentDate: LocalDateTime = convertDateToLocalDate(Date());
            val exerciseDate: LocalDateTime = convertDateToLocalDate(dateTime)

            if ( currentDate.toLocalDate().isEqual(exerciseDate.toLocalDate()) ) {
                return true;
            }

            return false;
        }
    }
}