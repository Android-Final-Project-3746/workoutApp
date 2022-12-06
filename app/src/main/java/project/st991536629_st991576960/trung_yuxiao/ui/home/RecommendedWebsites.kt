package project.st991536629_st991576960.trung_yuxiao.ui.home

import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.domain.WorkoutWebsiteModel
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType

class RecommendedWebsites {
    companion object {
        val websites = listOf(
            WorkoutWebsiteModel(
                url = "https://www.google.com",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Joggo",
                description = "Made for beginner. Beginnings are always the hardest. " +
                        "Combine needs and bevioural science to make running work for " +
                        "you and your goals - from day one",
                imageID = R.drawable.joggo,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://joggo.run/en-US/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Joggo",
                description = "Made for beginner. Beginnings are always the hardest. " +
                        "Combine needs and behavioural science to make running work for " +
                        "you and your goals - from day one",
                imageID = R.drawable.joggo,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://joggo.run/en-US/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Joggo",
                description = "Made for beginner. Beginnings are always the hardest. " +
                        "Combine needs and bevioural science to make running work for " +
                        "you and your goals - from day one",
                imageID = R.drawable.joggo,
                imageBackgroundColor = R.drawable.gradient_background_1
            )
        )
    }
}