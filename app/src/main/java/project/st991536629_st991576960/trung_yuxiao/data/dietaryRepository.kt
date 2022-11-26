package project.st991536629_st991576960.trung_yuxiao.data

import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import java.util.*

class dietaryRepository (private val dataSource: dietaryDataSource) {

    suspend fun addDietary(item: DietModel) = dataSource.add(item);

    suspend fun removeDietary(item: DietModel) = dataSource.remove(item)

    suspend fun updateDietary(item: DietModel) = dataSource.update(item)

    suspend fun getDietaryById(id: UUID): DietModel = dataSource.getById(id);

    suspend fun getAllDietary(): Flow<List<DietModel>> = dataSource.getAll();
}