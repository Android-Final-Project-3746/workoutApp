package project.st991536629_st991576960.trung_yuxiao.domain

import android.graphics.drawable.BitmapDrawable
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType

data class WorkoutWebsiteModel(
    val url: String,
    val workoutType: ExerciseType,
    val websiteName: String,
    val description: String,
    val imageID: Int,
    val imageBackgroundColor: Int,
)
