package project.st991536629_st991576960.trung_yuxiao.framework

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseDataSource
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.framework.database.ProjectDatabase
import project.st991536629_st991576960.trung_yuxiao.framework.entity.RunningExerciseEntity
import java.util.*

class RoomRunningExerciseDataSource(context: Context) : RunningExerciseDataSource {

    private val runningDao = ProjectDatabase.getInstance(context).runningDao();

    override suspend fun insert(item: RunningExercise) {
        runningDao.insert(RunningExerciseEntity(
            id = item.id,
            dateTime = item.dateTime,
            distance = item.distance,
            isDone = item.isDone,
        ))
    }

    override suspend fun remove(item: RunningExercise) {
        runningDao.delete( RunningExerciseEntity(
                id = item.id,
                dateTime = item.dateTime,
                distance = item.distance,
                isDone = item.isDone,
        ))
    }

    override suspend fun updates(item: RunningExercise) {
        runningDao.updates(
            RunningExerciseEntity(
                id = item.id,
                dateTime = item.dateTime,
                distance = item.distance,
                isDone = item.isDone,
        ))
    }

    override suspend fun getById(id: UUID): RunningExercise {
        val item = runningDao.getByID(id);

        return RunningExercise(
            id = item.id,
            dateTime = item.dateTime,
            distance = item.distance,
            isDone = item.isDone,
        )
    }

    override suspend fun deleteById(id: UUID) {
        runningDao.deleteById(id);
    }

    override fun getAll(): Flow<List<RunningExercise>> {
        return runningDao.getAll().transform { items ->

            val runningExercises = mutableListOf<RunningExercise>()

            for (item in items) {
                runningExercises.add( RunningExercise(
                    id = item.id,
                    dateTime = item.dateTime,
                    distance = item.distance,
                    isDone = item.isDone,
                ))
            }

            emit(runningExercises)
        }
    }
}