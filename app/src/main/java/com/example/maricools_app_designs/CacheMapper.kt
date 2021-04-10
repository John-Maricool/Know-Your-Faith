package com.example.maricools_app_designs

import com.example.maricools_app_designs.interfaces_kids.ModelMapper
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.models.PrayerServerModel
import javax.inject.Inject

class CacheMapper
@Inject
constructor(): ModelMapper<PrayerModel, PrayerServerModel> {

    override fun mapFromModel(model: PrayerServerModel): PrayerModel {
        return PrayerModel(
                prayerTitle = model.prayerTitle,
                prayerPart = model.prayerPart,
                prayerContent = model.prayerContent,
                uid = model.uid
        )
    }

    override fun mapToModel(model: PrayerModel): PrayerServerModel {
        return PrayerServerModel(model.uid, model.prayerTitle, model.prayerContent, model.prayerPart)
    }

    fun convertToCacheList(modelList: List<PrayerServerModel>): List<PrayerModel>{
       return modelList.map { mapFromModel(it) }
    }
}