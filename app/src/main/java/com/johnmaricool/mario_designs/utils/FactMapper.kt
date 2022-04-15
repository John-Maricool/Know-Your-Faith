package com.johnmaricool.mario_designs.utils

import com.johnmaricool.mario_designs.interfaces_kids.ModelMapper
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.models.FactServerModel
import javax.inject.Inject

class FactMapper
@Inject
constructor(): ModelMapper<FactModel, FactServerModel> {

    override fun mapFromModel(model: FactServerModel): FactModel {
        return FactModel(
                uid = model.uid,
                factTitle = model.factTitle,
                factContent = model.factContent
        )
    }

    override fun mapToModel(model: FactModel): FactServerModel {
        return FactServerModel(model.uid, model.factTitle, model.factContent)
    }

    fun convertToCacheList(modelList: List<FactServerModel>): List<FactModel>{
       return modelList.map { mapFromModel(it) }
    }
}