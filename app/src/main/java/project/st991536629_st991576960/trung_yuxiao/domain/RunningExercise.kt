package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.*


class RunningExercise(
    override val id: UUID,
    override val dateTime: Date,
    override val isDone: Boolean,
    val distance: Double,
) : Exercise


//data class RunningExercise(
//    val id: UUID,
//    val dateTime: Date,
//    val distance: Double,
//    val isDone: Boolean,
//)