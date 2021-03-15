package com.example.maricools_app_designs.utils.repositories

import android.content.Context
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.utils.models.FactModel
import dagger.hilt.android.qualifiers.ApplicationContext

class FactsListRepository
constructor(private val factsDao: FactDao,  @ApplicationContext val context: Context) {

     fun allCommandmentsTitles(): MutableList<FactModel>{
        return factsDao.getAllFacts(context.resources.getString(R.string.comm_header)).toMutableList()
     }

     fun allHolySpiritTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.holy_header)).toMutableList()
     }

     fun allOtherTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.other_header)) as MutableList<FactModel>
     }
     fun allSacramentTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.sac_header)) as MutableList<FactModel>
     }

     fun allPrayerTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.pray_header)) as MutableList<FactModel>
     }
     fun allSinTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.sin_header)) as MutableList<FactModel>
     }
     fun allCustomTitles(): MutableList<FactModel>{
         return factsDao.getAllFacts(context.resources.getString(R.string.custom_fact)) as MutableList<FactModel>
     }

    val commandment = context.resources.getString(R.string.comm_header)
    val sacrament = context.resources.getString(R.string.sac_header)
    val sin = context.resources.getString(R.string.sin_header)
    val prayer = context.resources.getString(R.string.pray_header)
    val holySpirit = context.resources.getString(R.string.holy_header)
    val others = context.resources.getString(R.string.other_header)
    val custom = context.resources.getString(R.string.custom_fact)

}