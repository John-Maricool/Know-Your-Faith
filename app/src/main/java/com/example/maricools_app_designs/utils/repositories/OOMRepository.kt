package com.example.maricools_app_designs.utils.repositories

import com.example.maricools_app_designs.database.OOMDao
import com.example.maricools_app_designs.utils.models.OOMModel

class OOMRepository(private val oomDao: OOMDao) {

    fun OOMPart(title: String): OOMModel {
        return  oomDao.getAllOrderTitles(title)
    }
}