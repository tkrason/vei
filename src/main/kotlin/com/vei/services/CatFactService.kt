package com.vei.services

import com.vei.client.catfact.CatFactClient
import com.vei.model.CatFact
import com.vei.repository.CatFactRepository
import com.mongodb.client.model.Filters
import org.koin.core.annotation.Singleton

@Singleton
class CatFactService(
    private val catFactClient: CatFactClient,
    private val catFactRepository: CatFactRepository,
) : ModelService<CatFact>(catFactRepository) {

    suspend fun getFactFromApi(): CatFact = catFactClient.getCatFact()

    suspend fun deleteWhereCatFactMatching(fact: String) = catFactRepository.deleteWhere {
        Filters.eq(CatFact::fact.name, fact)
    }
}
