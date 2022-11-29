package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import java.util.UUID

class PushUpExerciseRepository (private val dataSource: PushUpExerciseDataSource) {

    suspend fun addPushUp(pushUp: PushUpExercise) = dataSource.insert(pushUp);

    suspend fun removePushUp(pushUp: PushUpExercise) = dataSource.remove(pushUp);

    suspend fun editPushUp(pushUp: PushUpExercise) = dataSource.updates(pushUp);

    suspend fun getPushUpById(id: UUID) = dataSource.getById(id);

    suspend fun getAllPushUp(): Flow<List<PushUpExercise>> = dataSource.getAll();
}