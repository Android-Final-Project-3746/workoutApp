package project.st991536629_st991576960.trung_yuxiao.framework.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "push_up_exercises")
data class PushUpExerciseEntity (
    @PrimaryKey
    val id: UUID,
    val dateTime: Date,
    val times: Long,
    val isDone: Boolean,
)