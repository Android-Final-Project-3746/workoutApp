package project.st991536629_st991576960.trung_yuxiao

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import project.st991536629_st991576960.trung_yuxiao.data.DietaryRepository
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository


const val NOTIFICATION_CHANNEL_ID = "exercise_notification"

class WorkoutApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initializing repositories
        DietaryRepository.initialize(this);
        RunningExerciseRepository.initialize(this);
        PushUpExerciseRepository.initialize(this);


        // Create channel for Notification
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            val name = getString(R.string.notification_channel_name);
            val importance = NotificationManager.IMPORTANCE_DEFAULT;

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)

            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java);

            notificationManager.createNotificationChannel(channel);
        }
    }
}