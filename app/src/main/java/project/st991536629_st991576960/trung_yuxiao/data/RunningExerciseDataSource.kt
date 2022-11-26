package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import java.util.*

interface RunningExerciseDataSource {
    suspend fun add(item: RunningExercise);

    suspend fun remove(item: RunningExercise);

    suspend fun update(item: RunningExercise);

    suspend fun getById(id: UUID): RunningExercise;

    suspend fun getAll(): Flow<List<RunningExercise>>
}