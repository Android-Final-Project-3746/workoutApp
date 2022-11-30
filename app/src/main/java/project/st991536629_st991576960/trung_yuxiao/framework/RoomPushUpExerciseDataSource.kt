package project.st991536629_st991576960.trung_yuxiao.framework

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseDataSource
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.framework.database.ProjectDatabase
import project.st991536629_st991576960.trung_yuxiao.framework.entity.PushUpExerciseEntity
import java.util.*

class RoomPushUpExerciseDataSource(context: Context) : PushUpExerciseDataSource {

    private val pushUpDao = ProjectDatabase.getInstance(context).pushUpDao()

    override suspend fun insert(item: PushUpExercise) {
        pushUpDao.insert( PushUpExerciseEntity(
            id = item.id,
            dateTime = item.dateTime,
            times = item.times,
            isDone = item.isDone,
        )
        )
    }

    override suspend fun remove(item: PushUpExercise) {
        pushUpDao.delete( PushUpExerciseEntity(
            id = item.id,
            dateTime = item.dateTime,
            times = item.times,
            isDone = item.isDone,
        )
        )
    }

    override suspend fun updates(item: PushUpExercise) {
        pushUpDao.updates(
            PushUpExerciseEntity(
                id = item.id,
                dateTime = item.dateTime,
                times = item.times,
                isDone = item.isDone,
            )
        )
    }

    override suspend fun getById(id: UUID): PushUpExercise {
        val item = pushUpDao.getByID(id);

        return PushUpExercise(
            id = item.id,
            dateTime = item.dateTime,
            times = item.times,
            isDone = item.isDone,
        )
    }

    override suspend fun deleteById(id: UUID) {
        pushUpDao.deleteById(id);
    }

    override fun getAll(): Flow<List<PushUpExercise>> {
        return pushUpDao.getAll().transform { items ->

            val pushUpExercises = mutableListOf<PushUpExercise>()

            for (item in items) {
                pushUpExercises.add( PushUpExercise(
                    id = item.id,
                    dateTime = item.dateTime,
                    times = item.times,
                    isDone = item.isDone,
                ))
            }

            emit(pushUpExercises);
        }
    }
}