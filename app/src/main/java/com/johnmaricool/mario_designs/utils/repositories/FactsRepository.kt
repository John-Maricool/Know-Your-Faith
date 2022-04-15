package com.johnmaricool.mario_designs.utils.repositories

import com.johnmaricool.mario_designs.utils.FactsDaoImpl
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.models.FactsFavModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactsRepository
   @Inject constructor(val factsDao: FactsDaoImpl){

    suspend fun theFact(id: Int): FactModel {
        return factsDao.getFactId(id)
    }

    suspend fun allFacts(): List<FactModel> {
        return factsDao.getAllFacts()
    }

    suspend fun allFavFacts(): List<FactsFavModel> {
        return factsDao.getAllFavFacts()
    }
}