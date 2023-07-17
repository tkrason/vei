package com.vei.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Person(
    @BsonId override val id: ObjectId?,
    val name: String,
    val surname: String,
    val skills: List<Skill>,
    val hoursPerMonth: Int,
    val status: PersonStatus,
) : Model

data class Skill(
    val skillName: String,
    val seniority: SkillSeniority,
)

enum class SkillSeniority {
    JUNION,
    MID,
    SENIOR,
    LEAD,
}

enum class PersonStatus {
    ACTIVE,
    INACTIVE,
}
