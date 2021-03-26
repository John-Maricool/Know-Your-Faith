package com.example.maricools_app_designs

import com.example.maricools_app_designs.interfaces_kids.ModelMapper
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactServerModel
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.models.PrayerServerModel
import javax.inject.Inject

class FactMapper
@Inject
constructor(): ModelMapper<FactModel, FactServerModel> {

    override fun mapFromModel(model: FactServerModel): FactModel {
        return FactModel(
                factTitle = model.title,
                factContent = model.fact
        )
    }

    override fun mapToModel(model: FactModel): FactServerModel {
        return FactServerModel(model.factTitle, model.factContent)
    }

    fun convertToCacheList(modelList: List<FactServerModel>): List<FactModel>{
       return modelList.map { mapFromModel(it) }
    }
}