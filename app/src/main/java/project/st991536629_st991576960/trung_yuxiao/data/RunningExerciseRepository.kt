package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import java.util.*

class RunningExerciseRepository(private val dataSource: RunningExerciseDataSource ) {

    suspend fun addRunningExercise(item: RunningExercise) = dataSource.insert(item);

    suspend fun removeRunningExercise(item: RunningExercise) = dataSource.remove(item);

    suspend fun updateRunningExercise(item: RunningExercise) = dataSource.updates(item);

    suspend fun getRunningExerciseById(id: UUID) = dataSource.getById(id);

    suspend fun getAllRunningExercise(): Flow<List<RunningExercise>> = dataSource.getAll();
}