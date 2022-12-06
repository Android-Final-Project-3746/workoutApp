package project.st991536629_st991576960.trung_yuxiao.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.framework.entity.RunningExerciseEntity
import java.util.*


@Dao
interface RunningExerciseDao {

    @Query("SELECT * FROM running_exercises")
    fun getAll(): Flow<List<RunningExerciseEntity>>

    @Query("SELECT * FROM running_exercises")
    suspend fun getExercisesOneTime(): List<RunningExerciseEntity>

    @Query("SELECT * FROM running_exercises WHERE id=(:id)")
    suspend fun getByID(id: UUID): RunningExerciseEntity;

    @Update
    suspend fun updates(item: RunningExerciseEntity)

    @Insert
    suspend fun insert(item: RunningExerciseEntity)

    @Delete
    suspend fun delete(item: RunningExerciseEntity);

    @Query("DELETE FROM running_exercises WHERE id=(:id)")
    suspend fun deleteById(id: UUID)
}