package project.st991536629_st991576960.trung_yuxiao.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.framework.entity.DietEntity
import project.st991536629_st991576960.trung_yuxiao.framework.entity.PushUpExerciseEntity
import java.util.*

@Dao
interface PushUpExerciseDao {

    @Query("SELECT * FROM push_up_exercises")
    fun getAll(): Flow<List<PushUpExerciseEntity>>

    @Query("SELECT * FROM push_up_exercises")
    suspend fun getExercisesOneTime(): List<PushUpExerciseEntity>

    @Query("SELECT * FROM push_up_exercises WHERE id=(:id)")
    suspend fun getByID(id: UUID): PushUpExerciseEntity;

    @Update
    suspend fun updates(item: PushUpExerciseEntity)

    @Insert
    suspend fun insert(item: PushUpExerciseEntity)

    @Delete
    suspend fun delete(item: PushUpExerciseEntity);

    @Query("DELETE FROM push_up_exercises WHERE id=(:id)")
    suspend fun deleteById(id: UUID)

}