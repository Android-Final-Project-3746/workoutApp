package project.st991536629_st991576960.trung_yuxiao.domain

import java.time.LocalDateTime
import java.util.*

interface Exercise {
    val id: UUID;
    val dateTime: Date;
    val isDone: Boolean;
    fun updateID(id: UUID): Exercise;
    fun updateDateTime(dateTime: Date): Exercise;
    fun updateIsDone(isDone: Boolean): Exercise;
}