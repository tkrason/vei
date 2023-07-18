package com.vei.controller.person.dto

import com.vei.model.Person
import com.vei.model.PersonStatus
import com.vei.model.Skill
import com.vei.model.SkillSeniority
import com.vei.utils.extensions.toValidObjectIdOrThrow
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val id: String? = null,
    val name: String,
    val surname: String,
    val skills: List<SkillDto>,
    val hoursPerMonth: Int,
    val status: PersonStatus,
)

@Serializable
data class SkillDto(
    val skillName: String,
    val seniority: SkillSeniority,
)

fun Person.toDto() = PersonDto(
    id = id?.toHexString(),
    name = name,
    surname = surname,
    skills = skills.map { it.toDto() },
    hoursPerMonth = hoursPerMonth,
    status = status,
)

fun Skill.toDto() = SkillDto(
    skillName = skillName,
    seniority = seniority,
)

fun PersonDto.toModel() = Person(
    id = id?.toValidObjectIdOrThrow(),
    name = name,
    surname = surname,
    skills = skills.map { it.toModel() },
    hoursPerMonth = hoursPerMonth,
    status = status,
)

fun SkillDto.toModel() = Skill(
    skillName = skillName,
    seniority = seniority,
)
