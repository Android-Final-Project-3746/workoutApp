package project.st991536629_st991576960.trung_yuxiao.framework.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "running_exercises")
data class RunningExerciseEntity(
    @PrimaryKey
    val id: UUID,
    val dateTime: Date,
    val distance: Double,
    val isDone: Boolean,
)