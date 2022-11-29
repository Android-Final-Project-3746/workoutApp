package project.st991536629_st991576960.trung_yuxiao

import android.app.Application
import project.st991536629_st991576960.trung_yuxiao.data.DietaryRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.framework.RoomDietDataSource

class WorkoutApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DietaryRepository.initialize(this);
    }
}