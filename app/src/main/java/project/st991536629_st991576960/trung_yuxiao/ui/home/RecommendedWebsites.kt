package project.st991536629_st991576960.trung_yuxiao.ui.home

import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.domain.WorkoutWebsiteModel
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType

class RecommendedWebsites {
    companion object {
        val websites = listOf(
            WorkoutWebsiteModel(
                url = "https://www.trainerize.com/nutrition-coaching/",
                workoutType = ExerciseType.PUSHUP,
                websiteName = "TRAINERIZE",
                description = "Inspire clients to live healthier, starting with what they eat",
                imageID = R.drawable.trainerize,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://www.bodybuilding.com/fun/workout-plans-programs",
                workoutType = ExerciseType.RUNNING,
                websiteName = "BodyBuiding",
                description = "Comprehensive plans for your total fitness journey",
                imageID = R.drawable.bodybuilder,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://obefitness.com/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "OBE Fitness",
                description = "The veriety you crave, the music you love, the result you want",
                imageID = R.drawable.obe_fitness,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://www.youtube.com/@OFFICIALTHENXSTUDIOS",
                workoutType = ExerciseType.PUSHUP,
                websiteName = "OFFICIAL THENX STUDIOS",
                description = "6 PACK ABS workouts you can do anywhere if you are a" +
                        "beginner.",
                imageID = R.drawable.officialthenxstudios,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://madmuscles.com/step-goal",
                workoutType = ExerciseType.RUNNING,
                websiteName = "MAD MUSCLE",
                description = "Build You Perfect Body",
                imageID = R.drawable.madmuscle,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://joggo.run/en-US/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Joggo",
                description = "Made for beginner. Beginnings are always the hardest. " +
                        "Combine needs and bevioural science to make running work for " +
                        "you and your goals - from day one",
                imageID = R.drawable.joggo_logo,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://www.muscleandstrength.com/workout-routines",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Muscle And Strength",
                description = "The most comprehensive database of free workout routines anywhere!",
                imageID = R.drawable.free_workout_plan,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://fitnessprogramer.com/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Fitness Programmer",
                description = "With the workout builder, you can easily create the workout plan you want" +
                        "The workout plan you create shows how the exercises are done and which muscle groups they work.",
                imageID = R.drawable.fitness_programmer,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://unimeal.com/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Unimeal",
                description = "Unimeal is an app that helps to establish new healthy habit in a comfortable and pleasant way",
                imageID = R.drawable.unimeal,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://greatist.com/fitness/bodyweight-push-up-variations",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Greatist",
                description = "82 Push-Ups You Need to Know About",
                imageID = R.drawable.greatist,
                imageBackgroundColor = R.drawable.gradient_background_1
            ),
            WorkoutWebsiteModel(
                url = "https://www.healthifyme.com/blog/the-ultimate-7-day-gym-diet-plan/",
                workoutType = ExerciseType.RUNNING,
                websiteName = "Healthify ME",
                description = "The Ultimate 7 Days Gym Diet Plan",
                imageID = R.drawable.heathify_me,
                imageBackgroundColor = R.drawable.gradient_background_1
            )
        )
    }
}