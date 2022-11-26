package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.*

data class RunningExercise(
    val id: UUID,
    val dateTime: Date,
    val distance: Double,
    val isDone: Boolean,
)