package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.*


class RunningExercise(
    override val id: UUID,
    override val dateTime: Date,
    override val isDone: Boolean,
    val distance: Double,
) : Exercise {
    fun copy(id: UUID = this.id,
             dateTime: Date = this.dateTime,
             distance: Double = this.distance,
             isDone: Boolean = this.isDone) = RunningExercise(id, dateTime,isDone, distance);

    override fun updateID(value: UUID): Exercise {
        return RunningExercise(
            id = value,
            dateTime = this.dateTime,
            distance = this.distance,
            isDone = this.isDone
        )
    }

    override fun updateDateTime(value: Date): Exercise {
        return RunningExercise(
            id = this.id,
            dateTime = value,
            distance = this.distance,
            isDone = this.isDone
        )
    }

    override fun updateIsDone(value: Boolean): Exercise {
        return RunningExercise(
            id = this.id,
            dateTime = this.dateTime,
            distance = this.distance,
            isDone = value
        )
    }

}


//data class RunningExercise(
//    val id: UUID,
//    val dateTime: Date,
//    val distance: Double,
//    val isDone: Boolean,
//)