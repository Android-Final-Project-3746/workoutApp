package project.st991536629_st991576960.trung_yuxiao.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import project.st991536629_st991576960.trung_yuxiao.framework.database.RoomDietDataSource
import java.util.*

class DietaryRepository (private val dataSource: DietaryDataSource) {

    suspend fun addDietary(item: DietModel) = dataSource.insert(item);

    suspend fun removeDietary(item: DietModel) = dataSource.remove(item)

    suspend fun updateDietary(item: DietModel) = dataSource.updates(item)

    suspend fun getDietaryById(id: UUID): DietModel = dataSource.getById(id);

    fun getAllDietary(): Flow<List<DietModel>> = dataSource.getAll();

    companion object {

        private var INSTANCE: DietaryRepository? = null;

        fun initialize(context: Context) {
            if ( INSTANCE == null ) {
                INSTANCE = DietaryRepository(RoomDietDataSource(context))
            }
        }

        fun get(): DietaryRepository {
            return INSTANCE ?: throw IllegalStateException("DietaryRepository must initalized")
        }
    }
}