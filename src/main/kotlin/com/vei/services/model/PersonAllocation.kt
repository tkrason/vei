package com.vei.services.model

import com.vei.model.Client
import com.vei.model.FillableSlot
import com.vei.model.Person
import com.vei.model.Project
import com.vei.model.SlotOption
import java.time.LocalDate

data class PersonAllocation(
    val from: LocalDate,
    val to: LocalDate,
    val slot: FillableSlot,
    val slotOption: SlotOption,
    val person: Person,
    val project: Project,
    val client: Client,
)
