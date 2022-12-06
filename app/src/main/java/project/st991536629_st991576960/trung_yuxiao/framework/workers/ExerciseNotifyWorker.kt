package project.st991536629_st991576960.trung_yuxiao.framework.workers

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import project.st991536629_st991576960.trung_yuxiao.MainActivity
import project.st991536629_st991576960.trung_yuxiao.NOTIFICATION_CHANNEL_ID
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.utils.DateFormatUtil
import java.util.*

private const val TAG = "ExerciseNotifyWorker"

class ExerciseNotifyWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    override suspend fun doWork(): Result {

        Log.i(TAG, "Work request is triggered at ${Date()}");

        val todayRunningExercises = runningExerciseRepository.getAllOneTime().filter { exercise ->
            DateFormatUtil.compareCurrentDateToDate(exercise.dateTime);
        }

        val todayPushUpExercise = pushUpExerciseRepository.getAllOneTime().filter { exercise ->
            DateFormatUtil.compareCurrentDateToDate(exercise.dateTime)
        }

        return try {
            if (todayRunningExercises.isNotEmpty() || todayPushUpExercise.isNotEmpty()) {
                Log.i(TAG, "There are exercises for today");
                notifyUser()
            }

            Result.success();
        } catch (ex: Exception) {
            Log.e(TAG, "Notification failed")
            Result.failure();
        }
    }

    private fun notifyUser() {
        val intent = MainActivity.newIntent(context);
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val resources = context.resources;

        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker("Today Exercises")
            .setSmallIcon(R.drawable.fitness)
            .setContentTitle("Today Exercises")
            .setContentText("You have planned exercise today")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification);
    }
}