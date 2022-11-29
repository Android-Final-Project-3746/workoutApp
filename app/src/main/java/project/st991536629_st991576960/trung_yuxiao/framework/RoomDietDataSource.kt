package project.st991536629_st991576960.trung_yuxiao.framework

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import project.st991536629_st991576960.trung_yuxiao.data.DietaryDataSource
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import project.st991536629_st991576960.trung_yuxiao.framework.database.ProjectDatabase
import project.st991536629_st991576960.trung_yuxiao.framework.entity.DietEntity
import java.util.*

class RoomDietDataSource(context: Context) : DietaryDataSource {

    private val dietDao = ProjectDatabase.getInstance(context).dietDao();

    override suspend fun insert(item: DietModel) {
        dietDao.insert(DietEntity(
            id = item.id,
            dateTime = item.dateTime,
            food = item.food,
            quantity = item.quantity,
        ))
    }

    override suspend fun remove(item: DietModel) {
        dietDao.delete(DietEntity(
            id = item.id,
            dateTime = item.dateTime,
            food = item.food,
            quantity = item.quantity,
        ))
    }

    override suspend fun updates(item: DietModel) {
        dietDao.updates(
            DietEntity(
                id = item.id,
                dateTime = item.dateTime,
                food = item.food,
                quantity = item.quantity,
        ))
    }

    override suspend fun getById(id: UUID): DietModel {
        val value = dietDao.getDietByID(id)

        return DietModel(
            id = value.id,
            dateTime = value.dateTime,
            food = value.food,
            quantity = value.quantity,
        )
    }

    override fun getAll(): Flow<List<DietModel>> {
        return dietDao.getAll().transform { items ->

            val dietModels = mutableListOf<DietModel>()

            for (item in items) {
                dietModels.add(DietModel(
                    id = item.id,
                    dateTime = item.dateTime,
                    food = item.food,
                    quantity = item.quantity,
                ))
            }

            emit(dietModels)
        }
    }


}