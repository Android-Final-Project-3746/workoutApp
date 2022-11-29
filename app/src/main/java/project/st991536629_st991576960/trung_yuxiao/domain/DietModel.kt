package project.st991536629_st991576960.trung_yuxiao.domain

import java.util.Date
import java.util.UUID

data class DietModel(
    val id: UUID,
    val dateTime: Date,
    val food: String,
    val quantity: String,
)
