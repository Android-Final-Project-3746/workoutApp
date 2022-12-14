package project.st991536629_st991576960.trung_yuxiao

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import project.st991536629_st991576960.trung_yuxiao.databinding.ActivityMainBinding
import project.st991536629_st991576960.trung_yuxiao.framework.workers.ExerciseNotifyWorker
import java.util.concurrent.TimeUnit



private const val EXERCISE_NOTIFY_WORK = "EXERCISE_NOTIFY_WORK"

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_workout, R.id.navigation_dietary
            )
        )

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#559249")));
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val periodicRequest = PeriodicWorkRequestBuilder<ExerciseNotifyWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            EXERCISE_NOTIFY_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}