package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import java.util.*

interface DietaryDataSource {

    suspend fun insert(item: DietModel);

    suspend fun remove(item: DietModel);

    suspend fun updates(item: DietModel);

    suspend fun getById(id: UUID): DietModel;

    suspend fun deleteById(id: UUID);

    fun getAll(): Flow<List<DietModel>>
}