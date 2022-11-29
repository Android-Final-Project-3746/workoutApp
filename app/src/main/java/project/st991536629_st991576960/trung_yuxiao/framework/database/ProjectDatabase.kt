package project.st991536629_st991576960.trung_yuxiao.framework.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import project.st991536629_st991576960.trung_yuxiao.framework.entity.DietEntity
import project.st991536629_st991576960.trung_yuxiao.framework.entity.PushUpExerciseEntity
import project.st991536629_st991576960.trung_yuxiao.framework.entity.RunningExerciseEntity


@Database(entities = [DietEntity::class, RunningExerciseEntity::class, PushUpExerciseEntity::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract fun dietDao(): DietDao;
    abstract fun runningDao(): RunningExerciseDao;
    abstract fun pushUpDao(): PushUpExerciseDao;

    companion object {

        @Volatile
        private var INSTANCE: ProjectDatabase? = null;

        fun getInstance(context: Context): ProjectDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, ProjectDatabase::class.java, "project.db")
                    .fallbackToDestructiveMigration()
                    .build();
            }

            return INSTANCE ?: throw IllegalStateException("Database must be initialized")
        }
    }
}