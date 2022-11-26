package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import java.util.*

interface dietaryDataSource {

    suspend fun add(item: DietModel);

    suspend fun remove(item: DietModel);

    suspend fun update(item: DietModel);

    suspend fun getById(id: UUID): DietModel;

    suspend fun getAll(): Flow<List<DietModel>>
}