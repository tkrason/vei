package com.vei.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.vei.application.Mongo
import com.vei.model.FillableSlot
import com.vei.model.SlotOption
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

@Singleton
class FillableSlotRepository(
    mongo: Mongo,
) : MongoCrudRepository<FillableSlot>(mongo, "vei") {
    override fun MongoDatabase.selectRepositoryCollection() = getCollection<FillableSlot>("fillable-slot")

    suspend fun getAllSlotsOnProject(projectId: ObjectId) = withCollection {
        find(Filters.eq(FillableSlot::belongsToProject.name, projectId))
    }

    suspend fun addPersonInTheSlot(slotId: ObjectId, slotOption: SlotOption) = withCollection {
        updateOne(
            Filters.eq(Mongo.MONGO_ID_FIELD, slotId),
            Updates.pull(
                FillableSlot::poolOfPossibleFillables.name,
                Filters.eq(SlotOption::personId.name, slotOption.personId),
            ),
        )

        updateOne(
            Filters.eq(Mongo.MONGO_ID_FIELD, slotId),
            Updates.addToSet(FillableSlot::poolOfPossibleFillables.name, slotOption),
        )
    }

    suspend fun deletePersonFromSlot(slotId: ObjectId, personId: ObjectId) = withCollection {
        updateOne(
            Filters.eq(Mongo.MONGO_ID_FIELD, slotId),
            Updates.pull(
                FillableSlot::poolOfPossibleFillables.name,
                Filters.eq(SlotOption::personId.name, personId),
            ),
        )
    }
}
