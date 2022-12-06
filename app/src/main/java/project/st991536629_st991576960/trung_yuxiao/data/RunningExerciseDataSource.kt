package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import java.util.*

interface RunningExerciseDataSource {
    suspend fun insert(item: RunningExercise);

    suspend fun remove(item: RunningExercise);

    suspend fun updates(item: RunningExercise);

    suspend fun getById(id: UUID): RunningExercise;

    suspend fun deleteById(id: UUID);

    suspend fun getAllOneTime(): List<RunningExercise>;

    fun getAll(): Flow<List<RunningExercise>>
}