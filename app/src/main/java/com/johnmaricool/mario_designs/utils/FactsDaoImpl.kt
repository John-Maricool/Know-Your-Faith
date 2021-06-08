package com.johnmaricool.mario_designs.utils

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnmaricool.mario_designs.database.FactDao
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.models.FactsFavModel
import javax.inject.Inject

class FactsDaoImpl
@Inject constructor(val dao: FactDao){

    suspend fun addFact(fact: List<FactModel>) = dao.addFact(fact)

    suspend fun addFact(fact: FactModel) = dao.addFact(fact)

    suspend fun getFactId(id: Int): FactModel = dao.getFactId(id)

    suspend fun getAllFacts(): List<FactModel> = dao.getAllFacts()

    suspend fun addFavFact(fact: FactsFavModel) = dao.addFavFact(fact)

    fun getAllFavFacts(): List<FactsFavModel> = dao.getAllFavFacts()
}