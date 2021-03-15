package com.example.maricools_app_designs.utils.repositories

import androidx.lifecycle.LiveData
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.utils.models.FactsFavModel

class FavFactRepository(val factDao: FactDao) {

    fun getFavFacts(): LiveData<MutableList<FactsFavModel>> {
        return factDao.getAllFavFacts()
    }
}