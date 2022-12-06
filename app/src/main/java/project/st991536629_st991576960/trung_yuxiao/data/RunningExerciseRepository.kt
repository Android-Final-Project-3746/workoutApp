package project.st991536629_st991576960.trung_yuxiao.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.framework.database.RoomRunningExerciseDataSource
import java.util.*

class RunningExerciseRepository(private val dataSource: RunningExerciseDataSource ) {

    suspend fun addRunningExercise(item: RunningExercise) = dataSource.insert(item);

    suspend fun removeRunningExercise(item: RunningExercise) = dataSource.remove(item);

    suspend fun updateRunningExercise(item: RunningExercise) = dataSource.updates(item);

    suspend fun getRunningExerciseById(id: UUID) = dataSource.getById(id);

    suspend fun deleteById(id: UUID) = dataSource.deleteById(id);

    suspend fun getAllOneTime(): List<RunningExercise> = dataSource.getAllOneTime();

    fun getAllRunningExercise(): Flow<List<RunningExercise>> = dataSource.getAll();

    companion object {

        private var INSTANCE: RunningExerciseRepository? = null;

        fun initialize(context: Context) {
            if ( INSTANCE == null ) {
                INSTANCE = RunningExerciseRepository(RoomRunningExerciseDataSource(context))
            }
        }

        fun get(): RunningExerciseRepository {
            return INSTANCE ?: throw IllegalStateException("Running Exercise Repository must be initialized")
        }
    }
}