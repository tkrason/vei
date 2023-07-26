package com.vei.services.model

import com.vei.model.FillableSlot
import com.vei.model.Person
import com.vei.model.SlotOption

data class PersonWithStatus(
    val status: PersonStatusInTime,
    val specificSlotOption: SpecificSlotOption?,
    val person: Person,
)

data class SpecificSlotOption(
    val slot: FillableSlot,
    val slotOption: SlotOption,
)

enum class PersonStatusInTime {
    BENCH,
    PREBOOKED,
    HARDBOOKED_ON_SLOT,
}
