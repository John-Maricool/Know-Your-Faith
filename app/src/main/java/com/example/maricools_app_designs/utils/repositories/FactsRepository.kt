package com.example.maricools_app_designs.utils.repositories

import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactsFavModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FactsRepository
    constructor(private val factsDao: FactDao){

    fun theFact(id: Int): FactModel {
        return factsDao.getFactId(id)
    }
}