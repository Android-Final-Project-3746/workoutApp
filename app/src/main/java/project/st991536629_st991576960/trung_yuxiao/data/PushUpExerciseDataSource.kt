package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import java.util.UUID

interface PushUpExerciseDataSource {
    suspend fun add(item: PushUpExercise);

    suspend fun remove(item: PushUpExercise);

    suspend fun update(item: PushUpExercise);

    suspend fun getById(id: UUID): PushUpExercise;

    suspend fun getAll(): Flow<List<PushUpExercise>>

}