package project.st991536629_st991576960.trung_yuxiao.framework.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.framework.entity.DietEntity
import java.util.*


@Dao
interface DietDao {

    @Query("SELECT * FROM diets")
    fun getAll(): Flow<List<DietEntity>>

    @Query("SELECT * FROM diets WHERE id=(:id)")
    suspend fun getDietByID(id: UUID): DietEntity;

    @Update
    suspend fun updates(diet: DietEntity)

    @Insert
    suspend fun insert(diet: DietEntity)

    @Delete
    suspend fun delete(diet: DietEntity);

}