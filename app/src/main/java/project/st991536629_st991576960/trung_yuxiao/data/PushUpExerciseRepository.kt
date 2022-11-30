package project.st991536629_st991576960.trung_yuxiao.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.framework.RoomPushUpExerciseDataSource
import project.st991536629_st991576960.trung_yuxiao.framework.RoomRunningExerciseDataSource
import java.util.UUID

class PushUpExerciseRepository (private val dataSource: PushUpExerciseDataSource) {

    suspend fun addPushUp(pushUp: PushUpExercise) = dataSource.insert(pushUp);

    suspend fun removePushUp(pushUp: PushUpExercise) = dataSource.remove(pushUp);

    suspend fun editPushUp(pushUp: PushUpExercise) = dataSource.updates(pushUp);

    suspend fun getPushUpById(id: UUID) = dataSource.getById(id);

    fun getAllPushUp(): Flow<List<PushUpExercise>> = dataSource.getAll();

    companion object {

        private var INSTANCE: PushUpExerciseRepository? = null;

        fun initialize(context: Context) {
            if ( INSTANCE == null ) {
                INSTANCE = PushUpExerciseRepository(RoomPushUpExerciseDataSource(context))
            }
        }

        fun get(): PushUpExerciseRepository {
            return INSTANCE ?: throw IllegalStateException("Pushup Exercise Repository must be initialized")
        }
    }
}