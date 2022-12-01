package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.*


class PushUpExercise(
    override val id: UUID,
    override val dateTime: Date,
    override val isDone: Boolean,
    val times: Long,
) : Exercise {
    fun copy(id: UUID = this.id,
             dateTime: Date = this.dateTime,
             times: Long = this.times,
             isDone: Boolean = this.isDone) = PushUpExercise(id, dateTime,isDone, times);

    override fun updateID(value: UUID): Exercise {
        return PushUpExercise(
            id = value,
            dateTime = this.dateTime,
            times = this.times,
            isDone = this.isDone
        )
    }

    override fun updateDateTime(value: Date): Exercise {
        return PushUpExercise(
            id = this.id,
            dateTime = value,
            times = this.times,
            isDone = this.isDone
        )
    }

    override fun updateIsDone(value: Boolean): Exercise {
        return PushUpExercise(
            id = this.id,
            dateTime = this.dateTime,
            times = this.times,
            isDone = value
        )
    }
}

//data class PushUpExercise(
//    val id: UUID,
//    val dateTime: Date,
//    val times: Long,
//    val isDone: Boolean,
//)