package com.vei.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

data class FillableSlot(
    @BsonId override val id: ObjectId? = null,
    val product: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val dailyRate: Int,
    val requiredNumberOfFillables: Int,

    val belongsToProject: ObjectId,
    val poolOfPossibleFillables: List<SlotOption>,
) : Model

data class SlotOption(
    val personId: ObjectId,
    val state: SlotOptionState,
    val fte: Int,
)

enum class SlotOptionState {
    PREBOOKED,
    HARDBOOKED,
}
