package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.*


class PushUpExercise(
    override val id: UUID,
    override val dateTime: Date,
    override val isDone: Boolean,
    val times: Long,
) : Exercise

//data class PushUpExercise(
//    val id: UUID,
//    val dateTime: Date,
//    val times: Long,
//    val isDone: Boolean,
//)